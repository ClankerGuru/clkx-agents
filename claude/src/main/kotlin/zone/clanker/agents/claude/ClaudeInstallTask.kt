package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeInstallTask : DefaultTask() {
    @TaskAction
    fun run() {
        val args =
            buildList {
                add("install")
                val target = project.findProperty("target")?.toString()
                if (!target.isNullOrEmpty()) add(target)
            }

        val result = Cli.exec("claude", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("claude install exited with code ${result.exitCode}")
    }
}
