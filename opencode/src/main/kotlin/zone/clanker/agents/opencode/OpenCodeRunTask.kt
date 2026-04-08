package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeRunTask : DefaultTask() {
    @Internal
    lateinit var extension: OpenCode.SettingsExtension

    @TaskAction
    fun run() {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")

        val args = buildArgs(prompt)
        val result = Cli.exec("opencode", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode exited with code ${result.exitCode}")
    }

    internal fun buildArgs(prompt: String): List<String> =
        buildList {
            add("--prompt")
            add(prompt)
            addFlag("--model", extension.model)
            addFlag("--format", extension.format)
            addFlag("--agent", extension.agent)
            addFlag("--variant", extension.variant)
            if (extension.thinking) add("--thinking")
            extension.file.forEach { addFlag("--file", it) }
            addFlag("--dir", extension.dir)
            if (extension.share) add("--share")
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
