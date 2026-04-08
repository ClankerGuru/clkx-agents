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
    const val TASK_MODELS = "opencode-models"
    const val TASK_EXPORT = "opencode-export"
    const val TASK_IMPORT = "opencode-import"
    const val TASK_MCP_LIST = "opencode-mcp-list"
    const val TASK_MCP_ADD = "opencode-mcp-add"
    const val TASK_SESSION_LIST = "opencode-session-list"
    const val TASK_SESSION_DELETE = "opencode-session-delete"
    const val TASK_AGENT_LIST = "opencode-agent-list"
    const val TASK_AGENT_CREATE = "opencode-agent-create"
    const val TASK_SERVE = "opencode-serve"
    const val TASK_UPGRADE = "opencode-upgrade"
    const val TASK_PR = "opencode-pr"
    const val TASK_GITHUB_INSTALL = "opencode-github-install"
    const val TASK_GITHUB_RUN = "opencode-github-run"

    open class SettingsExtension {
        var model: String = ""
        var format: String = ""
        var agent: String = ""
        var variant: String = ""
        var thinking: Boolean = false
        var file: List<String> = emptyList()
        var dir: String = ""
        var share: Boolean = false
        var pure: Boolean = false
        var title: String = ""
        var continueSession: Boolean = false
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
        registerCoreTasks(rootProject, extension)
        registerDataTasks(rootProject)
        registerMcpTasks(rootProject)
        registerSessionTasks(rootProject)
        registerAgentTasks(rootProject)
        registerGithubTasks(rootProject)
    }

    private fun registerCoreTasks(
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
        rootProject.tasks.register(TASK_SERVE, OpenCodeServeTask::class.java) {
            it.group = GROUP
            it.description = "Start OpenCode server"
        }
        rootProject.tasks.register(TASK_UPGRADE, OpenCodeUpgradeTask::class.java) {
            it.group = GROUP
            it.description = "Upgrade OpenCode"
        }
        rootProject.tasks.register(TASK_PR, OpenCodePrTask::class.java) {
            it.group = GROUP
            it.description = "Review a pull request with OpenCode"
        }
    }

    private fun registerDataTasks(rootProject: Project) {
        rootProject.tasks.register(TASK_MODELS, OpenCodeModelsTask::class.java) {
            it.group = GROUP
            it.description = "List OpenCode models"
        }
        rootProject.tasks.register(TASK_EXPORT, OpenCodeExportTask::class.java) {
            it.group = GROUP
            it.description = "Export OpenCode session"
        }
        rootProject.tasks.register(TASK_IMPORT, OpenCodeImportTask::class.java) {
            it.group = GROUP
            it.description = "Import OpenCode session"
        }
    }

    private fun registerMcpTasks(rootProject: Project) {
        rootProject.tasks.register(TASK_MCP_LIST, OpenCodeMcpListTask::class.java) {
            it.group = GROUP
            it.description = "List OpenCode MCP servers"
        }
        rootProject.tasks.register(TASK_MCP_ADD, OpenCodeMcpAddTask::class.java) {
            it.group = GROUP
            it.description = "Add an OpenCode MCP server"
        }
    }

    private fun registerSessionTasks(rootProject: Project) {
        rootProject.tasks.register(TASK_SESSION_LIST, OpenCodeSessionListTask::class.java) {
            it.group = GROUP
            it.description = "List OpenCode sessions"
        }
        rootProject.tasks.register(TASK_SESSION_DELETE, OpenCodeSessionDeleteTask::class.java) {
            it.group = GROUP
            it.description = "Delete an OpenCode session"
        }
    }

    private fun registerAgentTasks(rootProject: Project) {
        rootProject.tasks.register(TASK_AGENT_LIST, OpenCodeAgentListTask::class.java) {
            it.group = GROUP
            it.description = "List OpenCode agents"
        }
        rootProject.tasks.register(TASK_AGENT_CREATE, OpenCodeAgentCreateTask::class.java) {
            it.group = GROUP
            it.description = "Create an OpenCode agent"
        }
    }

    private fun registerGithubTasks(rootProject: Project) {
        rootProject.tasks.register(TASK_GITHUB_INSTALL, OpenCodeGithubInstallTask::class.java) {
            it.group = GROUP
            it.description = "Install OpenCode GitHub integration"
        }
        rootProject.tasks.register(TASK_GITHUB_RUN, OpenCodeGithubRunTask::class.java) {
            it.group = GROUP
            it.description = "Run OpenCode GitHub action"
        }
    }
}
