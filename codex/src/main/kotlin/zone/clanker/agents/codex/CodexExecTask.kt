package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli
import zone.clanker.agents.exec.addFlag

@UntrackedTask(because = "Executes external CLI")
open class CodexExecTask : DefaultTask() {
    @Internal
    lateinit var extension: Codex.SettingsExtension

    internal fun buildCommand(): Pair<String, List<String>> {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")
        return "codex" to buildArgs(prompt)
    }

    @TaskAction
    fun run() {
        if (extension.dangerouslyBypass) {
            logger.warn("codex: --dangerously-bypass-approvals-and-sandbox is enabled")
        }
        val (binary, args) = buildCommand()
        Cli.execAndPrint(
            binary,
            args,
            workDir = project.projectDir,
            label = "codex",
            timeoutSeconds = extension.timeoutSeconds,
        )
    }

    internal fun buildArgs(prompt: String): List<String> =
        buildList {
            add(prompt)
            addFlag("--model", extension.model)
            addFlag("--sandbox", extension.sandbox)
            addFlag("--approval", extension.approval)
            if (extension.fullAuto) add("--full-auto")
            if (extension.search) add("--search")
            extension.addDir.forEach { addFlag("--add-dir", it) }
            addFlag("--output-schema", extension.outputSchema)
            if (extension.json) add("--json")
            if (extension.ephemeral) add("--ephemeral")
            extension.image.forEach { addFlag("--image", it) }
            if (extension.dangerouslyBypass) add("--dangerously-bypass-approvals-and-sandbox")
            addAll(extension.extraArgs)
        }
}
