package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexCompletionTask : DefaultTask() {
    @TaskAction
    fun run() {
        val shell =
            project.findProperty("shell")?.toString()
                ?: error("Required property 'shell' not set. Use -Pshell=bash|zsh|fish")

        val result = Cli.exec("codex", listOf("completion", shell), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex completion exited with code ${result.exitCode}")
    }
}
