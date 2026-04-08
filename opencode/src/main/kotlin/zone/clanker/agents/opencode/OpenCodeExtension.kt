package zone.clanker.agents.opencode

open class OpenCodeExtension {
    var model: String = ""
    var format: String = ""
    var agent: String = ""
    var variant: String = ""
    var thinking: Boolean = false
    var file: List<String> = emptyList()
    var dir: String = ""
    var share: Boolean = false
    var extraArgs: List<String> = emptyList()
}
