package zone.clanker.agents.codex

import org.gradle.api.Plugin
import org.gradle.api.Project

class CodexPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create("codex", CodexExtension::class.java)

        project.tasks.register("codex-exec", CodexExecTask::class.java) {
            it.group = "codex"
            it.description = "Run OpenAI Codex with a prompt"
            it.extension = ext
        }
        project.tasks.register("codex-review", CodexReviewTask::class.java) {
            it.group = "codex"
            it.description = "Run Codex code review"
            it.extension = ext
        }
        project.tasks.register("codex-auth", CodexAuthTask::class.java) {
            it.group = "codex"
            it.description = "Check Codex login status"
        }
        project.tasks.register("codex-version", CodexVersionTask::class.java) {
            it.group = "codex"
            it.description = "Show Codex version"
        }
    }
}
