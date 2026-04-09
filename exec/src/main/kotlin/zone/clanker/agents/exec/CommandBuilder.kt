package zone.clanker.agents.exec

fun MutableList<String>.addFlag(
    flag: String,
    enabled: Boolean,
) {
    if (enabled) add(flag)
}

fun MutableList<String>.addFlag(
    flag: String,
    value: String,
) {
    if (value.isNotEmpty()) {
        add(flag)
        add(value)
    }
}
