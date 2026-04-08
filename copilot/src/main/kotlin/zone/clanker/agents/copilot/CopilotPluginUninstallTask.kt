package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotPluginUninstallTask : DefaultTask() {
    @TaskAction
    fun run() {
        val name =
            project.findProperty("name")?.toString()
                ?: error("Required property 'name' not set. Use -Pname=\"...\"")

        val result = Cli.exec("copilot", listOf("plugin", "uninstall", name), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("copilot plugin uninstall exited with code ${result.exitCode}")
    }
}
