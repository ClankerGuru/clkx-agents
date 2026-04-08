package zone.clanker.agents.opencode

import org.gradle.api.Plugin
import org.gradle.api.Project

class OpenCodePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create("opencode", OpenCodeExtension::class.java)

        project.tasks.register("opencode-run", OpenCodeRunTask::class.java) {
            it.group = "opencode"
            it.description = "Run OpenCode with a prompt"
            it.extension = ext
        }
        project.tasks.register("opencode-auth", OpenCodeAuthTask::class.java) {
            it.group = "opencode"
            it.description = "List OpenCode providers"
        }
        project.tasks.register("opencode-version", OpenCodeVersionTask::class.java) {
            it.group = "opencode"
            it.description = "Show OpenCode version"
        }
        project.tasks.register("opencode-stats", OpenCodeStatsTask::class.java) {
            it.group = "opencode"
            it.description = "Show OpenCode usage stats"
        }
    }
}
