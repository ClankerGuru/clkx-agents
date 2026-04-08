# Exec

Core process execution library used by all agent plugins. Not a Gradle plugin itself -- it provides the `Cli` utility that each agent module uses to shell out to its respective CLI binary.

## API

### `Cli.exec`

```kotlin
// Simple form
val result = Cli.exec("claude", listOf("--version"))

// Full form with CliRequest
val result = Cli.exec(CliRequest(
    binary = "claude",
    args = listOf("-p", "Hello"),
    workDir = projectDir,
    env = mapOf("ANTHROPIC_API_KEY" to key),
    timeoutSeconds = 600,
    stdin = "input data",
))
```

### `Cli.which`

```kotlin
if (Cli.which("claude")) {
    // binary is on PATH
}
```

### `CliResult`

```kotlin
data class CliResult(
    val exitCode: Int,
    val stdout: String,
    val stderr: String,
) {
    val success: Boolean get() = exitCode == 0
}
```

### `CliRequest`

```kotlin
data class CliRequest(
    val binary: String,
    val args: List<String> = emptyList(),
    val workDir: File? = null,
    val env: Map<String, String> = emptyMap(),
    val timeoutSeconds: Long = 300L,
    val stdin: String? = null,
)
```
