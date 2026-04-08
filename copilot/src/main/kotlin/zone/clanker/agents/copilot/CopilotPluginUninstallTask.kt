package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotPluginUninstallTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val name =
            project.findProperty("name")?.toString()
                ?: error("Required property 'name' not set. Use -Pname=\"...\"")
        return "copilot" to listOf("plugin", "uninstall", name)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "copilot plugin uninstall")
    }
}
