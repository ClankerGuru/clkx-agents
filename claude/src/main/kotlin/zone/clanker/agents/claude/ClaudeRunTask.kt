package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli
import zone.clanker.agents.exec.addFlag

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
        if (extension.dangerouslySkipPermissions) {
            logger.warn("claude: --dangerously-skip-permissions is enabled")
        }
        val (binary, args) = buildCommand()
        Cli.execAndPrint(
            binary,
            args,
            workDir = project.projectDir,
            label = "claude",
            timeoutSeconds = extension.timeoutSeconds,
        )
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
            extension.allowedTools.forEach { addFlag("--allowed-tools", it) }
            extension.disallowedTools.forEach { addFlag("--disallowed-tools", it) }
            if (extension.bare) add("--bare")
            if (extension.dangerouslySkipPermissions) add("--dangerously-skip-permissions")
            if (extension.verbose) add("--verbose")
            extension.addDir.forEach { addFlag("--add-dir", it) }
            addFlag("--append-system-prompt", extension.appendSystemPrompt)
            addAll(extension.extraArgs)
        }
}
