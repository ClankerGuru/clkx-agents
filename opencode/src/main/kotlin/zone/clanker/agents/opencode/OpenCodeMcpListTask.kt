package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeMcpListTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "opencode" to listOf("mcp", "list")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode mcp list")
    }
}
