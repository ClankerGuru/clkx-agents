plugins { id("clkx-conventions") }

dependencies { implementation(project(":exec")) }

gradlePlugin {
    plugins {
        register("codex") {
            id = "zone.clanker.codex"
            implementationClass = "zone.clanker.agents.codex.Codex\$SettingsPlugin"
            displayName = "OpenAI Codex Plugin"
            description = "Gradle tasks for OpenAI Codex CLI"
        }
    }
}
