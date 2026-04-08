plugins { id("clkx-conventions") }

dependencies { implementation(project(":exec")) }

gradlePlugin {
    plugins {
        register("opencode") {
            id = "zone.clanker.opencode"
            implementationClass = "zone.clanker.agents.opencode.OpenCode\$SettingsPlugin"
            displayName = "OpenCode Plugin"
            description = "Gradle tasks for OpenCode CLI"
        }
    }
}
