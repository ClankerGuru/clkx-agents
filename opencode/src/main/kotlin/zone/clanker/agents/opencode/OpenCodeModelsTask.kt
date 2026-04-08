package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeModelsTask : DefaultTask() {
    @TaskAction
    fun run() {
        val args =
            buildList {
                add("models")
                val provider = project.findProperty("provider")?.toString()
                if (!provider.isNullOrEmpty()) {
                    add("--provider")
                    add(provider)
                }
            }

        val result = Cli.exec("opencode", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode models exited with code ${result.exitCode}")
    }
}
