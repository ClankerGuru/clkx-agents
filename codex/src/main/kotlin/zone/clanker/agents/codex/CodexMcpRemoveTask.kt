package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexMcpRemoveTask : DefaultTask() {
    @TaskAction
    fun run() {
        val name =
            project.findProperty("name")?.toString()
                ?: error("Required property 'name' not set. Use -Pname=\"...\"")

        val result = Cli.exec("codex", listOf("mcp", "remove", name), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex mcp remove exited with code ${result.exitCode}")
    }
}
