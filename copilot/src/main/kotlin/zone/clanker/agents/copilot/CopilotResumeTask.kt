package zone.clanker.agents.copilot

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CopilotResumeTask : DefaultTask() {
    @Internal
    lateinit var extension: CopilotExtension

    @TaskAction
    fun run() {
        val sessionId =
            project.findProperty("sessionId")?.toString()
                ?: error("Required property 'sessionId' not set. Use -PsessionId=\"...\"")

        val args =
            buildList {
                add("--resume")
                add(sessionId)
                addAll(extension.extraArgs)
            }

        val result = Cli.exec("copilot", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("copilot exited with code ${result.exitCode}")
    }
}
