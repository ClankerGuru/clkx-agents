package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeServeTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> = "opencode" to listOf("serve")

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        val pid = Cli.execDaemon(binary, args, workDir = project.projectDir)
        println("Started $binary (PID: $pid)")
    }
}
