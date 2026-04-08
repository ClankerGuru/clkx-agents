package zone.clanker.agents.claude

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class ClaudeDoctorTask : DefaultTask() {
    @TaskAction
    fun run() {
        val result = Cli.exec("claude", listOf("doctor"), workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("claude doctor exited with code ${result.exitCode}")
    }
}
