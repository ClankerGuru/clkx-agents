package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeSetupTokenTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("claude", listOf("setup-token"), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("claude setup-token exited with code ${result.exitCode}")
    }
}
