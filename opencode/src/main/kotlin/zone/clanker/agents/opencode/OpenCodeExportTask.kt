package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeExportTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val args =
            buildList {
                add("export")
                val sessionId = project.findProperty("sessionId")?.toString()
                if (!sessionId.isNullOrEmpty()) {
                    add("--session")
                    add(sessionId)
                }
            }
        return "opencode" to args
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode export")
    }
}
