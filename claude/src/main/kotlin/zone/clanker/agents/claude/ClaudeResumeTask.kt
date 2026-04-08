package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeResumeTask : DefaultTask() {
    @Internal
    lateinit var extension: Claude.SettingsExtension

    internal fun buildCommand(): Pair<String, List<String>> {
        val sessionId =
            project.findProperty("sessionId")?.toString()
                ?: error("Required property 'sessionId' not set. Use -PsessionId=\"...\"")

        val args =
            buildList {
                add("-r")
                add(sessionId)
                val prompt = project.findProperty("prompt")?.toString()
                if (!prompt.isNullOrEmpty()) {
                    add("-p")
                    add(prompt)
                }
                if (project.findProperty("forkSession")?.toString()?.toBoolean() == true) add("--fork")
                addAll(extension.extraArgs)
            }
        return "claude" to args
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "claude")
    }
}
