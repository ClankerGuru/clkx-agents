package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeMcpAddTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val name =
            project.findProperty("name")?.toString()
                ?: error("Required property 'name' not set. Use -Pname=\"...\"")
        val transport =
            project.findProperty("transport")?.toString()
                ?: error("Required property 'transport' not set. Use -Ptransport=\"...\"")
        return "claude" to listOf("mcp", "add", name, transport)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "claude mcp add")
    }
}
