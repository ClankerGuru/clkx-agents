package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeUpdateTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "claude" to listOf("update")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "claude update")
    }
}
