package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodePrTask : DefaultTask() {
    @TaskAction
    fun run() {
        val number =
            project.findProperty("number")?.toString()
                ?: error("Required property 'number' not set. Use -Pnumber=\"...\"")

        val result = Cli.exec("opencode", listOf("pr", number), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("opencode pr exited with code ${result.exitCode}")
    }
}
