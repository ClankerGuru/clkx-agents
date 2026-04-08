package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeImportTask : DefaultTask() {
    @TaskAction
    fun run() {
        val file =
            project.findProperty("file")?.toString()
                ?: error("Required property 'file' not set. Use -Pfile=\"...\"")

        val result = Cli.exec("opencode", listOf("import", file), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode import exited with code ${result.exitCode}")
    }
}
