plugins { id("clkx-plugin") }

dependencies { implementation(project(":exec")) }

gradlePlugin {
    plugins {
        register("opencode") {
            id = "zone.clanker.opencode"
            implementationClass = "zone.clanker.agents.opencode.OpenCodePlugin"
            displayName = "OpenCode Plugin"
            description = "Gradle tasks for OpenCode CLI"
        }
    }
}
