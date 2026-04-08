package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeMcpRemoveTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val name =
            project.findProperty("mcpName")?.toString()
                ?: error("Required property 'mcpName' not set. Use -PmcpName=\"...\"")
        return "claude" to listOf("mcp", "remove", name)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "claude mcp remove")
    }
}
