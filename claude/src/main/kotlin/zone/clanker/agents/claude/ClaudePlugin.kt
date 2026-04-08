package zone.clanker.agents.claude

import org.gradle.api.Plugin
import org.gradle.api.Project

class ClaudePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create("claude", ClaudeExtension::class.java)

        project.tasks.register("claude-run", ClaudeRunTask::class.java) {
            it.group = "claude"
            it.description = "Run Claude Code with a prompt"
            it.extension = ext
        }
        project.tasks.register("claude-resume", ClaudeResumeTask::class.java) {
            it.group = "claude"
            it.description = "Resume a Claude Code session"
            it.extension = ext
        }
        project.tasks.register("claude-auth", ClaudeAuthTask::class.java) {
            it.group = "claude"
            it.description = "Check Claude Code auth status"
        }
        project.tasks.register("claude-version", ClaudeVersionTask::class.java) {
            it.group = "claude"
            it.description = "Show Claude Code version"
        }
        project.tasks.register("claude-doctor", ClaudeDoctorTask::class.java) {
            it.group = "claude"
            it.description = "Run Claude Code doctor"
        }
    }
}
