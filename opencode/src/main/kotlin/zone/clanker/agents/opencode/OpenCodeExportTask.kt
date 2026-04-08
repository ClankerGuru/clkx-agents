package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeExportTask : DefaultTask() {
    @TaskAction
    fun run() {
        val args =
            buildList {
                add("export")
                val sessionId = project.findProperty("sessionId")?.toString()
                if (!sessionId.isNullOrEmpty()) {
                    add("--session")
                    add(sessionId)
                }
            }

        val result = Cli.exec("opencode", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode export exited with code ${result.exitCode}")
    }
}
