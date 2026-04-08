package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexCloudListTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("codex", listOf("cloud", "list"), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex cloud list exited with code ${result.exitCode}")
    }
}
