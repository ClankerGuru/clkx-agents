plugins { id("clkx-conventions") }

dependencies { implementation(project(":exec")) }

gradlePlugin {
    plugins {
        register("claude") {
            id = "zone.clanker.claude"
            implementationClass = "zone.clanker.agents.claude.Claude\$SettingsPlugin"
            displayName = "Claude Code Plugin"
            description = "Gradle tasks for Claude Code CLI"
        }
    }
}
