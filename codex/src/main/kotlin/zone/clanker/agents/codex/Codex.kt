package zone.clanker.agents.codex

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

data object Codex {
    const val GROUP = "codex"
    const val EXTENSION_NAME = "codex"
    const val TASK_EXEC = "codex-exec"
    const val TASK_REVIEW = "codex-review"
    const val TASK_AUTH = "codex-auth"
    const val TASK_VERSION = "codex-version"

    open class SettingsExtension {
        var model: String = ""
        var sandbox: String = ""
        var approval: String = ""
        var fullAuto: Boolean = false
        var search: Boolean = false
        var addDir: List<String> = emptyList()
        var outputSchema: String = ""
        var json: Boolean = false
        var ephemeral: Boolean = false
        var image: List<String> = emptyList()
        var extraArgs: List<String> = emptyList()
    }

    class SettingsPlugin : Plugin<Settings> {
        override fun apply(settings: Settings) {
            val extension = settings.extensions.create(EXTENSION_NAME, SettingsExtension::class.java)
            settings.gradle.rootProject(
                Action { rootProject ->
                    registerTasks(rootProject, extension)
                },
            )
        }
    }

    internal fun registerTasks(
        rootProject: Project,
        extension: SettingsExtension,
    ) {
        rootProject.tasks.register(TASK_EXEC, CodexExecTask::class.java) {
            it.group = GROUP
            it.description = "Run OpenAI Codex with a prompt"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_REVIEW, CodexReviewTask::class.java) {
            it.group = GROUP
            it.description = "Run Codex code review"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_AUTH, CodexAuthTask::class.java) {
            it.group = GROUP
            it.description = "Check Codex login status"
        }
        rootProject.tasks.register(TASK_VERSION, CodexVersionTask::class.java) {
            it.group = GROUP
            it.description = "Show Codex version"
        }
    }
}
