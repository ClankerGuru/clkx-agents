package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeVersionTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("claude", listOf("--version"))
        print(result.stdout)
        if (!result.success) error("claude --version exited with code ${result.exitCode}")
    }
}
