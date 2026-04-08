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

    @TaskAction
    fun run() {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")

        val args = buildArgs(prompt)
        val result = Cli.exec("claude", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("claude exited with code ${result.exitCode}")
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
