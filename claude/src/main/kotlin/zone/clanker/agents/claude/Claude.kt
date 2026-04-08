package zone.clanker.agents.claude

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

data object Claude {
    const val GROUP = "claude"
    const val EXTENSION_NAME = "claude"
    const val TASK_RUN = "claude-run"
    const val TASK_RESUME = "claude-resume"
    const val TASK_AUTH = "claude-auth"
    const val TASK_VERSION = "claude-version"
    const val TASK_DOCTOR = "claude-doctor"
    const val TASK_AGENTS = "claude-agents"
    const val TASK_MCP_LIST = "claude-mcp-list"
    const val TASK_MCP_ADD = "claude-mcp-add"
    const val TASK_MCP_REMOVE = "claude-mcp-remove"
    const val TASK_MCP_SERVE = "claude-mcp-serve"
    const val TASK_UPDATE = "claude-update"
    const val TASK_INSTALL = "claude-install"
    const val TASK_SETUP_TOKEN = "claude-setup-token"

    open class SettingsExtension {
        var model: String = ""
        var outputFormat: String = "text"
        var permissionMode: String = "default"
        var maxBudgetUsd: Double = 0.0
        var systemPrompt: String = ""
        var allowedTools: List<String> = emptyList()
        var disallowedTools: List<String> = emptyList()
        var effort: String = ""
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
        rootProject.tasks.register(TASK_RUN, ClaudeRunTask::class.java) {
            it.group = GROUP
            it.description = "Run Claude Code with a prompt"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_RESUME, ClaudeResumeTask::class.java) {
            it.group = GROUP
            it.description = "Resume a Claude Code session"
            it.extension = extension
        }
        rootProject.tasks.register(TASK_AUTH, ClaudeAuthTask::class.java) {
            it.group = GROUP
            it.description = "Check Claude Code auth status"
        }
        rootProject.tasks.register(TASK_VERSION, ClaudeVersionTask::class.java) {
            it.group = GROUP
            it.description = "Show Claude Code version"
        }
        rootProject.tasks.register(TASK_DOCTOR, ClaudeDoctorTask::class.java) {
            it.group = GROUP
            it.description = "Run Claude Code doctor"
        }
        rootProject.tasks.register(TASK_AGENTS, ClaudeAgentsTask::class.java) {
            it.group = GROUP
            it.description = "List configured Claude agents"
        }
        rootProject.tasks.register(TASK_MCP_LIST, ClaudeMcpListTask::class.java) {
            it.group = GROUP
            it.description = "List Claude MCP servers"
        }
        rootProject.tasks.register(TASK_MCP_ADD, ClaudeMcpAddTask::class.java) {
            it.group = GROUP
            it.description = "Add a Claude MCP server"
        }
        rootProject.tasks.register(TASK_MCP_REMOVE, ClaudeMcpRemoveTask::class.java) {
            it.group = GROUP
            it.description = "Remove a Claude MCP server"
        }
        rootProject.tasks.register(TASK_MCP_SERVE, ClaudeMcpServeTask::class.java) {
            it.group = GROUP
            it.description = "Start Claude MCP server"
        }
        rootProject.tasks.register(TASK_UPDATE, ClaudeUpdateTask::class.java) {
            it.group = GROUP
            it.description = "Update Claude Code"
        }
        rootProject.tasks.register(TASK_INSTALL, ClaudeInstallTask::class.java) {
            it.group = GROUP
            it.description = "Install Claude Code"
        }
        rootProject.tasks.register(TASK_SETUP_TOKEN, ClaudeSetupTokenTask::class.java) {
            it.group = GROUP
            it.description = "Set up Claude API token"
        }
    }
}
