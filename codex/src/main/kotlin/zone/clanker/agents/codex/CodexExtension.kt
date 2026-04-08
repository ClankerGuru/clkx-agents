package zone.clanker.agents.codex

open class CodexExtension {
    var model: String = ""
    var sandbox: String = ""
    var approval: String = ""
    var fullAuto: Boolean = false
    var search: Boolean = false
    var addDir: List<String> = emptyList()
    var outputSchema: String = ""
    var json: Boolean = false
    var ephemeral: Boolean = false
    var image: List<String> = emptyList()
    var extraArgs: List<String> = emptyList()
}
