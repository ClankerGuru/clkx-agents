package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexResumeTask : DefaultTask() {
    @TaskAction
    fun run() {
        val args =
            buildList {
                add("resume")
                if (project.findProperty("last")?.toString()?.toBoolean() == true) add("--last")
            }

        val result = Cli.exec("codex", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex resume exited with code ${result.exitCode}")
    }
}
