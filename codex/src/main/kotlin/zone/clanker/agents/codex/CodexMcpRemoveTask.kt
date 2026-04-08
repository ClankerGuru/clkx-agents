package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexMcpRemoveTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val name =
            project.findProperty("name")?.toString()
                ?: error("Required property 'name' not set. Use -Pname=\"...\"")
        return "codex" to listOf("mcp", "remove", name)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "codex mcp remove")
    }
}
