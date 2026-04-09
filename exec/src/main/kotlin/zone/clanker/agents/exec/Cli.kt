package zone.clanker.agents.exec

import org.gradle.api.logging.Logging
import java.io.File
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
    private val logger = Logging.getLogger(Cli::class.java)

    fun exec(request: CliRequest): CliResult {
        val command = listOf(request.binary) + request.args
        val process =
            runCatching {
                ProcessBuilder(command)
                    .directory(request.workDir)
                    .also { pb -> request.env.forEach { (k, v) -> pb.environment()[k] = v } }
                    .start()
            }.getOrElse { e ->
                return CliResult(-1, "", "Failed to start ${request.binary}: ${e.message}")
            }

        if (request.stdin != null) {
            process.outputStream.bufferedWriter().use { it.write(request.stdin) }
        }

        var stdout = ""
        var stderr = ""
        val stdoutThread = Thread { stdout = process.inputStream.bufferedReader().readText() }
        val stderrThread = Thread { stderr = process.errorStream.bufferedReader().readText() }
        stdoutThread.start()
        stderrThread.start()
        val completed = process.waitFor(request.timeoutSeconds, TimeUnit.SECONDS)

        return if (!completed) {
            process.destroyForcibly()
            stdoutThread.join()
            stderrThread.join()
            CliResult(-1, stdout, "Process timed out after ${request.timeoutSeconds}s")
        } else {
            stdoutThread.join()
            stderrThread.join()
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
        timeoutSeconds: Long = CliRequest.DEFAULT_TIMEOUT_SECONDS,
    ): CliResult {
        if (which(binary) == null) {
            error("'$binary' not found. Install it first.")
        }
        val request =
            CliRequest(
                binary = binary,
                args = args,
                workDir = workDir,
                timeoutSeconds = timeoutSeconds,
            )
        val result = exec(request)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) logger.warn(result.stderr)
        if (!result.success) error("$label exited with code ${result.exitCode}")
        return result
    }

    fun execDaemon(
        binary: String,
        args: List<String> = emptyList(),
        workDir: File? = null,
    ): Long {
        val command = listOf(binary) + args
        val process =
            ProcessBuilder(command)
                .directory(workDir)
                .inheritIO()
                .start()
        return process.pid()
    }

    fun which(binary: String): String? {
        val result = exec(binary = "which", args = listOf(binary))
        return if (result.success) result.stdout.trim() else null
    }
}
