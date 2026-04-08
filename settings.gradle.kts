pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "clkx-agents"

include(":exec")
include(":claude")
include(":copilot")
include(":codex")
include(":opencode")
