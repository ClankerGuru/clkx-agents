package zone.clanker.agents.copilot

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

data object Copilot {
    const val GROUP = "copilot"
    const val EXTENSION_NAME = "copilot"
    const val TASK_RUN = "copilot-run"
    const val TASK_RESUME = "copilot-resume"
    const val TASK_INIT = "copilot-init"
    const val TASK_AUTH = "copilot-auth"
    const val TASK_VERSION = "copilot-version"
    const val TASK_UPDATE = "copilot-update"
    const val TASK_PLUGIN_LIST = "copilot-plugin-list"
    const val TASK_PLUGIN_INSTALL = "copilot-plugin-install"
    const val TASK_PLUGIN_UNINSTALL = "copilot-plugin-uninstall"

    open class SettingsExtension {
        var model: String = ""
        var effort: String = ""
        var outputFormat: String = ""
        var allowAll: Boolean = false
        var allowAllTools: Boolean = false
        var allowAllPaths: Boolean = false
        var autopilot: Boolean = false
        var silent: Boolean = false
        var agent: String = ""
        var addDir: List<String> = emptyList()
        var configDir: String = ""
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
        rootProject.tasks.register(TASK_RUN, CopilotRunTask::class.java) {
            it.group = GROUP
            it.description = "Run GitHub Copilot with a prompt"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_RESUME, CopilotResumeTask::class.java) {
            it.group = GROUP
            it.description = "Resume a Copilot session"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_INIT, CopilotInitTask::class.java) {
            it.group = GROUP
            it.description = "Initialize Copilot in the project"
        }
        rootProject.tasks.register(TASK_AUTH, CopilotAuthTask::class.java) {
            it.group = GROUP
            it.description = "Log in to GitHub Copilot"
        }
        rootProject.tasks.register(TASK_VERSION, CopilotVersionTask::class.java) {
            it.group = GROUP
            it.description = "Show Copilot version"
        }
        rootProject.tasks.register(TASK_UPDATE, CopilotUpdateTask::class.java) {
            it.group = GROUP
            it.description = "Update Copilot"
        }
        rootProject.tasks.register(TASK_PLUGIN_LIST, CopilotPluginListTask::class.java) {
            it.group = GROUP
            it.description = "List Copilot plugins"
        }
        rootProject.tasks.register(TASK_PLUGIN_INSTALL, CopilotPluginInstallTask::class.java) {
            it.group = GROUP
            it.description = "Install a Copilot plugin"
        }
        rootProject.tasks.register(TASK_PLUGIN_UNINSTALL, CopilotPluginUninstallTask::class.java) {
            it.group = GROUP
            it.description = "Uninstall a Copilot plugin"
        }
    }
}
