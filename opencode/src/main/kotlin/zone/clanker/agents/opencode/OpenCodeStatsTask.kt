package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeStatsTask : DefaultTask() {
    @TaskAction
    fun run() {
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

        val result = Cli.exec("opencode", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode stats exited with code ${result.exitCode}")
    }
}
