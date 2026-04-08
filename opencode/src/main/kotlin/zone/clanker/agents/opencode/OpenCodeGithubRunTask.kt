package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeGithubRunTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("opencode", listOf("github", "run"), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode github run exited with code ${result.exitCode}")
    }
}
