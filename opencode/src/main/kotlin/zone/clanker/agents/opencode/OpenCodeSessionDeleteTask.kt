package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeSessionDeleteTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val sessionId =
            project.findProperty("sessionId")?.toString()
                ?: error("Required property 'sessionId' not set. Use -PsessionId=\"...\"")
        return "opencode" to listOf("session", "delete", sessionId)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode session delete")
    }
}
