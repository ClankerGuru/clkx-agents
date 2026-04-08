package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeRunTask : DefaultTask() {
    @Internal
    lateinit var extension: Claude.SettingsExtension

    internal fun buildCommand(): Pair<String, List<String>> {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")
        return "claude" to buildArgs(prompt)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "claude")
    }

    internal fun buildArgs(prompt: String): List<String> =
        buildList {
            add("-p")
            add(prompt)
            addFlag("--model", extension.model)
            add("--output-format")
            add(extension.outputFormat)
            if (extension.permissionMode != "default") addFlag("--permission-mode", extension.permissionMode)
            addFlag("--effort", extension.effort)
            if (extension.maxBudgetUsd > 0) addFlag("--max-budget-usd", extension.maxBudgetUsd.toString())
            addFlag("--system-prompt", extension.systemPrompt)
            extension.allowedTools.forEach { addFlag("--allowedTools", it) }
            extension.disallowedTools.forEach { addFlag("--disallowedTools", it) }
            if (extension.bare) add("--bare")
            if (extension.dangerouslySkipPermissions) add("--dangerously-skip-permissions")
            if (extension.verbose) add("--verbose")
            extension.addDir.forEach { addFlag("--add-dir", it) }
            addFlag("--append-system-prompt", extension.appendSystemPrompt)
            addAll(extension.extraArgs)
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
