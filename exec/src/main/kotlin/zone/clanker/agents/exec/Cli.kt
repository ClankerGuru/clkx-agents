package zone.clanker.agents.exec

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

data class CliResult(
    val exitCode: Int,
    val stdout: String,
    val stderr: String,
) {
    val success: Boolean get() = exitCode == 0
}

data class CliRequest(
    val binary: String,
    val args: List<String> = emptyList(),
    val workDir: File? = null,
    val env: Map<String, String> = emptyMap(),
    val timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS,
    val stdin: String? = null,
) {
    companion object {
        const val DEFAULT_TIMEOUT_SECONDS = 300L
    }
}

object Cli {
    fun exec(request: CliRequest): CliResult {
        val command = listOf(request.binary) + request.args
        val process =
            try {
                ProcessBuilder(command)
                    .directory(request.workDir)
                    .also { pb -> request.env.forEach { (k, v) -> pb.environment()[k] = v } }
                    .start()
            } catch (e: IOException) {
                return CliResult(-1, "", e.message ?: "Failed to start process")
            }

        if (request.stdin != null) {
            process.outputStream.bufferedWriter().use { it.write(request.stdin) }
        }

        val stdout = process.inputStream.bufferedReader().readText()
        val stderr = process.errorStream.bufferedReader().readText()
        val completed = process.waitFor(request.timeoutSeconds, TimeUnit.SECONDS)

        return if (!completed) {
            process.destroyForcibly()
            CliResult(-1, stdout, "Process timed out after ${request.timeoutSeconds}s")
        } else {
            CliResult(process.exitValue(), stdout, stderr)
        }
    }

    fun exec(
        binary: String,
        args: List<String> = emptyList(),
        workDir: File? = null,
    ): CliResult = exec(CliRequest(binary = binary, args = args, workDir = workDir))

    fun execAndPrint(
        binary: String,
        args: List<String> = emptyList(),
        workDir: File? = null,
        label: String = "$binary ${args.firstOrNull() ?: ""}".trim(),
    ): CliResult {
        val result = exec(binary, args, workDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("$label exited with code ${result.exitCode}")
        return result
    }

    fun which(binary: String): Boolean = exec(binary = "which", args = listOf(binary)).success
}
