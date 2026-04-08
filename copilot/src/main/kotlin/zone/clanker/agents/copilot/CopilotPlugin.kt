package zone.clanker.agents.copilot

import org.gradle.api.Plugin
import org.gradle.api.Project

class CopilotPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create("copilot", CopilotExtension::class.java)

        project.tasks.register("copilot-run", CopilotRunTask::class.java) {
            it.group = "copilot"
            it.description = "Run GitHub Copilot with a prompt"
            it.extension = ext
        }
        project.tasks.register("copilot-resume", CopilotResumeTask::class.java) {
            it.group = "copilot"
            it.description = "Resume a Copilot session"
            it.extension = ext
        }
        project.tasks.register("copilot-init", CopilotInitTask::class.java) {
            it.group = "copilot"
            it.description = "Initialize Copilot in the project"
        }
        project.tasks.register("copilot-auth", CopilotAuthTask::class.java) {
            it.group = "copilot"
            it.description = "Log in to GitHub Copilot"
        }
        project.tasks.register("copilot-version", CopilotVersionTask::class.java) {
            it.group = "copilot"
            it.description = "Show Copilot version"
        }
    }
}
