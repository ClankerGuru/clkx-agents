pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("clkx-settings")
}

rootProject.name = "clkx-agents"

include(":exec")
include(":claude")
include(":copilot")
include(":codex")
include(":opencode")
