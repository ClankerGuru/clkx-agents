package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeStatsTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val args =
            buildList {
                add("stats")
                val days = project.findProperty("days")?.toString()
                if (!days.isNullOrEmpty()) {
                    add("--days")
                    add(days)
                }
                val tools = project.findProperty("tools")?.toString()
                if (!tools.isNullOrEmpty()) {
                    add("--tools")
                    add(tools)
                }
                val models = project.findProperty("models")?.toString()
                if (!models.isNullOrEmpty()) {
                    add("--models")
                    add(models)
                }
                val proj = project.findProperty("statsProject")?.toString()
                if (!proj.isNullOrEmpty()) {
                    add("--project")
                    add(proj)
                }
            }
        return "opencode" to args
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode stats")
    }
}
