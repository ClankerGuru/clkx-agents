package zone.clanker.agents.codex

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import zone.clanker.agents.exec.Cli

@UntrackedTask(because = "Executes external CLI")
open class CodexReviewTask : DefaultTask() {
    @Internal
    lateinit var extension: Codex.SettingsExtension

    @TaskAction
    fun run() {
        val args =
            buildList {
                add("review")
                val base = project.findProperty("base")?.toString()
                if (!base.isNullOrEmpty()) {
                    add("--base")
                    add(base)
                }
                val commit = project.findProperty("commit")?.toString()
                if (!commit.isNullOrEmpty()) {
                    add("--commit")
                    add(commit)
                }
                if (project.findProperty("uncommitted")?.toString()?.toBoolean() == true) add("--uncommitted")
                val title = project.findProperty("title")?.toString()
                if (!title.isNullOrEmpty()) {
                    add("--title")
                    add(title)
                }
                addAll(extension.extraArgs)
            }

        val result = Cli.exec("codex", args, workDir = project.projectDir)
        print(result.stdout)
        if (result.stderr.isNotEmpty()) System.err.print(result.stderr)
        if (!result.success) error("codex review exited with code ${result.exitCode}")
    }
}
