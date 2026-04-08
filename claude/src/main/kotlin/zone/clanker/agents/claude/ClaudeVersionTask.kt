package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeVersionTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "claude" to listOf("--version")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, label = "claude --version")
    }
}
