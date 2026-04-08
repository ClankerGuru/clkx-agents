package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotResumeTask : DefaultTask() {
    @Internal
    lateinit var extension: Copilot.SettingsExtension

    internal fun buildCommand(): Pair<String, List<String>> {
        val sessionId =
            project.findProperty("sessionId")?.toString()
                ?: error("Required property 'sessionId' not set. Use -PsessionId=\"...\"")

        val args =
            buildList {
                add("--resume")
                add(sessionId)
                addAll(extension.extraArgs)
            }
        return "copilot" to args
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "copilot")
    }
}
