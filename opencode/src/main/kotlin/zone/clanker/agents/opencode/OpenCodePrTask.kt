package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodePrTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val number =
            project.findProperty("number")?.toString()
                ?: error("Required property 'number' not set. Use -Pnumber=\"...\"")
        return "opencode" to listOf("pr", number)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode pr")
    }
}
