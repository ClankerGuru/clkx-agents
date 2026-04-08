package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeVersionTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("opencode", listOf("--version"))
        print(result.stdout)
        if (!result.success) error("opencode --version exited with code ${result.exitCode}")
    }
}
