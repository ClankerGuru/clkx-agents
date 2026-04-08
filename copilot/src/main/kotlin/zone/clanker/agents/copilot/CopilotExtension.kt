package zone.clanker.agents.copilot

open class CopilotExtension {
    var model: String = ""
    var effort: String = ""
    var outputFormat: String = ""
    var allowAll: Boolean = false
    var allowAllTools: Boolean = false
    var allowAllPaths: Boolean = false
    var autopilot: Boolean = false
    var silent: Boolean = false
    var agent: String = ""
    var addDir: List<String> = emptyList()
    var configDir: String = ""
    var extraArgs: List<String> = emptyList()
}
