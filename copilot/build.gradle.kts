plugins { id("clkx-plugin") }

dependencies { implementation(project(":exec")) }

gradlePlugin {
    plugins {
        register("copilot") {
            id = "zone.clanker.copilot"
            implementationClass = "zone.clanker.agents.copilot.CopilotPlugin"
            displayName = "GitHub Copilot Plugin"
            description = "Gradle tasks for GitHub Copilot CLI"
        }
    }
}
