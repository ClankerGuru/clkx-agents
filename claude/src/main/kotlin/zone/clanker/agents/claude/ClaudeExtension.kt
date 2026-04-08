package zone.clanker.agents.claude

open class ClaudeExtension {
    var model: String = ""
    var outputFormat: String = "text"
    var permissionMode: String = "default"
    var maxBudgetUsd: Double = 0.0
    var systemPrompt: String = ""
    var allowedTools: List<String> = emptyList()
    var disallowedTools: List<String> = emptyList()
    var effort: String = ""
    var extraArgs: List<String> = emptyList()
}
