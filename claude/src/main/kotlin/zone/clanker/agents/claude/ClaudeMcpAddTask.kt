package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeMcpAddTask : DefaultTask() {
    @TaskAction
    fun run() {
        val name =
            project.findProperty("name")?.toString()
                ?: error("Required property 'name' not set. Use -Pname=\"...\"")
        val transport =
            project.findProperty("transport")?.toString()
                ?: error("Required property 'transport' not set. Use -Ptransport=\"...\"")

        val args =
            buildList {
                add("mcp")
                add("add")
                add(name)
                add(transport)
            }

        val result = Cli.exec("claude", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("claude mcp add exited with code ${result.exitCode}")
    }
}
