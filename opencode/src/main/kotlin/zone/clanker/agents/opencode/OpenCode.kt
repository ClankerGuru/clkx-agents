package zone.clanker.agents.opencode

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

data object OpenCode {
    const val GROUP = "opencode"
    const val EXTENSION_NAME = "opencode"
    const val TASK_RUN = "opencode-run"
    const val TASK_AUTH = "opencode-auth"
    const val TASK_VERSION = "opencode-version"
    const val TASK_STATS = "opencode-stats"

    open class SettingsExtension {
        var model: String = ""
        var format: String = ""
        var agent: String = ""
        var variant: String = ""
        var thinking: Boolean = false
        var file: List<String> = emptyList()
        var dir: String = ""
        var share: Boolean = false
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
        rootProject.tasks.register(TASK_RUN, OpenCodeRunTask::class.java) {
            it.group = GROUP
            it.description = "Run OpenCode with a prompt"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_AUTH, OpenCodeAuthTask::class.java) {
            it.group = GROUP
            it.description = "List OpenCode providers"
        }
        rootProject.tasks.register(TASK_VERSION, OpenCodeVersionTask::class.java) {
            it.group = GROUP
            it.description = "Show OpenCode version"
        }
        rootProject.tasks.register(TASK_STATS, OpenCodeStatsTask::class.java) {
            it.group = GROUP
            it.description = "Show OpenCode usage stats"
        }
    }
}
