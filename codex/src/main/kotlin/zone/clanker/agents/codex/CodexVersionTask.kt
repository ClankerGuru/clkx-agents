package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexVersionTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("codex", listOf("--version"))
        print(result.stdout)
        if (!result.success) error("codex --version exited with code ${result.exitCode}")
    }
}
