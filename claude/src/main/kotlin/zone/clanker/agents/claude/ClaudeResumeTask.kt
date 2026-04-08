package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeResumeTask : DefaultTask() {
    @Internal
    lateinit var extension: ClaudeExtension

    @TaskAction
    fun run() {
        val sessionId =
            project.findProperty("sessionId")?.toString()
                ?: error("Required property 'sessionId' not set. Use -PsessionId=\"...\"")

        val args =
            buildList {
                add("-r")
                add(sessionId)
                val prompt = project.findProperty("prompt")?.toString()
                if (!prompt.isNullOrEmpty()) {
                    add("-p")
                    add(prompt)
                }
                if (project.findProperty("forkSession")?.toString()?.toBoolean() == true) add("--fork")
                addAll(extension.extraArgs)
            }

        val result = Cli.exec("claude", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("claude exited with code ${result.exitCode}")
    }
}
