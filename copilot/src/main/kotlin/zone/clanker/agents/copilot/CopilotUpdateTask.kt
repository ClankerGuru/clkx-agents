package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotUpdateTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "copilot" to listOf("update")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "copilot update")
    }
}
