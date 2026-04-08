package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeUpgradeTask : DefaultTask() {
    @TaskAction
    fun run() {
        val args =
            buildList {
                add("upgrade")
                val target = project.findProperty("target")?.toString()
                if (!target.isNullOrEmpty()) add(target)
            }

        val result = Cli.exec("opencode", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode upgrade exited with code ${result.exitCode}")
    }
}
