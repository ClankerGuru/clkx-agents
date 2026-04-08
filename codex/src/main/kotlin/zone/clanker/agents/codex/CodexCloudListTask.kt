package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexCloudListTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "codex" to listOf("cloud", "list")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "codex cloud list")
    }
}
