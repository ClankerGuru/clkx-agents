package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeModelsTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val args =
            buildList {
                add("models")
                val provider = project.findProperty("provider")?.toString()
                if (!provider.isNullOrEmpty()) {
                    add("--provider")
                    add(provider)
                }
            }
        return "opencode" to args
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode models")
    }
}
