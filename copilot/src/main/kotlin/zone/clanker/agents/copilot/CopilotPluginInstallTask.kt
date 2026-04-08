package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotPluginInstallTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val source =
            project.findProperty("source")?.toString()
                ?: error("Required property 'source' not set. Use -Psource=\"...\"")
        return "copilot" to listOf("plugin", "install", source)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "copilot plugin install")
    }
}
