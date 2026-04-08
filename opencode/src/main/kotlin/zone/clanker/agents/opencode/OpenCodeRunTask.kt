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

    internal fun buildCommand(): Pair<String, List<String>> {
        val prompt =
            project.findProperty("prompt")?.toString()
                ?: error("Required property 'prompt' not set. Use -Pprompt=\"...\"")
        return "opencode" to buildArgs(prompt)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode")
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
            if (extension.pure) add("--pure")
            addFlag("--title", extension.title)
            if (extension.continueSession) add("--continue")
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
