package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexCompletionTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val shell =
            project.findProperty("shell")?.toString()
                ?: error("Required property 'shell' not set. Use -Pshell=bash|zsh|fish")
        return "codex" to listOf("completion", shell)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "codex completion")
    }
}
