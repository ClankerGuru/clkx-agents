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
    const val TASK_APPLY = "codex-apply"
    const val TASK_RESUME = "codex-resume"
    const val TASK_FORK = "codex-fork"
    const val TASK_MCP_LIST = "codex-mcp-list"
    const val TASK_MCP_ADD = "codex-mcp-add"
    const val TASK_MCP_REMOVE = "codex-mcp-remove"
    const val TASK_FEATURES = "codex-features"
    const val TASK_COMPLETION = "codex-completion"
    const val TASK_CLOUD_LIST = "codex-cloud-list"

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
        rootProject.tasks.register(TASK_APPLY, CodexApplyTask::class.java) {
            it.group = GROUP
            it.description = "Apply latest Codex diff"
        }
        rootProject.tasks.register(TASK_RESUME, CodexResumeTask::class.java) {
            it.group = GROUP
            it.description = "Resume a Codex session"
        }
        rootProject.tasks.register(TASK_FORK, CodexForkTask::class.java) {
            it.group = GROUP
            it.description = "Fork a Codex session"
        }
        rootProject.tasks.register(TASK_MCP_LIST, CodexMcpListTask::class.java) {
            it.group = GROUP
            it.description = "List Codex MCP servers"
        }
        rootProject.tasks.register(TASK_MCP_ADD, CodexMcpAddTask::class.java) {
            it.group = GROUP
            it.description = "Add a Codex MCP server"
        }
        rootProject.tasks.register(TASK_MCP_REMOVE, CodexMcpRemoveTask::class.java) {
            it.group = GROUP
            it.description = "Remove a Codex MCP server"
        }
        rootProject.tasks.register(TASK_FEATURES, CodexFeaturesTask::class.java) {
            it.group = GROUP
            it.description = "List Codex features"
        }
        rootProject.tasks.register(TASK_COMPLETION, CodexCompletionTask::class.java) {
            it.group = GROUP
            it.description = "Generate Codex shell completions"
        }
        rootProject.tasks.register(TASK_CLOUD_LIST, CodexCloudListTask::class.java) {
            it.group = GROUP
            it.description = "List Codex cloud sessions"
        }
    }
}
