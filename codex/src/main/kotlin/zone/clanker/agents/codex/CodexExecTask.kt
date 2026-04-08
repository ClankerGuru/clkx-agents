package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexExecTask : DefaultTask() {
    @Internal
    lateinit var extension: Codex.SettingsExtension

    @TaskAction
    fun run() {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")

        val args = buildArgs(prompt)
        val result = Cli.exec("codex", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex exited with code ${result.exitCode}")
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
