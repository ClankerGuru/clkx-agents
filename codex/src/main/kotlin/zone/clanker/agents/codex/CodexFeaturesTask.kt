package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexFeaturesTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("codex", listOf("features", "list"), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex features list exited with code ${result.exitCode}")
    }
}
