package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotRunTask : DefaultTask() {
    @Internal
    lateinit var extension: Copilot.SettingsExtension

    internal fun buildCommand(): Pair<String, List<String>> {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")
        return "copilot" to buildArgs(prompt)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "copilot")
    }

    internal fun buildArgs(prompt: String): List<String> =
        buildList {
            add("-m")
            add(prompt)
            addFlag("--model", extension.model)
            addFlag("--effort", extension.effort)
            addFlag("--output-format", extension.outputFormat)
            addBooleanFlags()
            addFlag("--agent", extension.agent)
            extension.addDir.forEach { addFlag("--add-dir", it) }
            addFlag("--config-dir", extension.configDir)
            addAll(extension.extraArgs)
        }

    private fun MutableList<String>.addBooleanFlags() {
        if (extension.allowAll) add("--allow-all")
        if (extension.allowAllTools) add("--allow-all-tools")
        if (extension.allowAllPaths) add("--allow-all-paths")
        if (extension.autopilot) add("--autopilot")
        if (extension.silent) add("--silent")
    }

    private fun MutableList<String>.addFlag(
        flag: String,
        value: String,
    ) {
        if (value.isNotEmpty()) {
            add(flag)
            add(value)
        }
    }
}
