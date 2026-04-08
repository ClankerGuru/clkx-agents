package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotVersionTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "copilot" to listOf("version")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, label = "copilot version")
    }
}
