package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotVersionTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("copilot", listOf("version"))
        print(result.stdout)
        if (!result.success) error("copilot version exited with code ${result.exitCode}")
    }
}
