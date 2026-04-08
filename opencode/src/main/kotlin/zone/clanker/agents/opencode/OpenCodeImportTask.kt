package zone.clanker.agents.opencode

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class OpenCodeImportTask : DefaultTask() {
    internal fun buildCommand(): Pair<String, List<String>> {
        val file =
            project.findProperty("file")?.toString()
                ?: error("Required property 'file' not set. Use -Pfile=\"...\"")
        return "opencode" to listOf("import", file)
    }

    @TaskAction
    fun run() {
        val (binary, args) = buildCommand()
        Cli.execAndPrint(binary, args, workDir = project.projectDir, label = "opencode import")
    }
}
