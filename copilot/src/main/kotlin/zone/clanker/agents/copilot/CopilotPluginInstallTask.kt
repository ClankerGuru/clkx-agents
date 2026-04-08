package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotPluginInstallTask : DefaultTask() {
    @TaskAction
    fun run() {
        val source =
            project.findProperty("source")?.toString()
                ?: error("Required property 'source' not set. Use -Psource=\"...\"")

        val result = Cli.exec("copilot", listOf("plugin", "install", source), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("copilot plugin install exited with code ${result.exitCode}")
    }
}
