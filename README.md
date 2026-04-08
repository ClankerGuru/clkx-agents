# clkx-agents

[![Build](https://github.com/ClankerGuru/clkx-agents/actions/workflows/build.yml/badge.svg)](https://github.com/ClankerGuru/clkx-agents/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/zone.clanker/clkx-agents)](https://central.sonatype.com/namespace/zone.clanker)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Gradle plugins for AI coding agents -- Claude Code, GitHub Copilot, OpenAI Codex, and OpenCode.

## Modules

| Module | Plugin ID | Binary | Description |
|--------|-----------|--------|-------------|
| `:exec` | -- | -- | Core process execution utility |
| `:claude` | `zone.clanker.claude` | `claude` | Claude Code CLI |
| `:copilot` | `zone.clanker.copilot` | `copilot` | GitHub Copilot CLI |
| `:codex` | `zone.clanker.codex` | `codex` | OpenAI Codex CLI |
| `:opencode` | `zone.clanker.opencode` | `opencode` | OpenCode CLI |

## Usage

Apply the plugin for the agent you want to use:

```kotlin
plugins {
    id("zone.clanker.claude") version "<version>"
}
```

### Claude Code

```bash
# Run with a prompt
./gradlew claude-run -Pprompt="Fix the build"

# Resume a session
./gradlew claude-resume -PsessionId="abc123"

# Check auth / version / health
./gradlew claude-auth
./gradlew claude-version
./gradlew claude-doctor
```

Configure defaults in `build.gradle.kts`:

```kotlin
claude {
    model = "claude-sonnet-4-20250514"
    outputFormat = "json"
    permissionMode = "bypassPermissions"
    effort = "high"
}
```

### GitHub Copilot

```bash
./gradlew copilot-run -Pprompt="Refactor the data layer"
./gradlew copilot-resume -PsessionId="abc123"
./gradlew copilot-init
./gradlew copilot-auth
./gradlew copilot-version
```

Configure defaults:

```kotlin
copilot {
    model = "gpt-4o"
    autopilot = true
    allowAll = true
}
```

### OpenAI Codex

```bash
./gradlew codex-exec -Pprompt="Add unit tests"
./gradlew codex-review -Pbase="main"
./gradlew codex-auth
./gradlew codex-version
```

Configure defaults:

```kotlin
codex {
    model = "codex-mini-latest"
    fullAuto = true
    sandbox = "docker"
}
```

### OpenCode

```bash
./gradlew opencode-run -Pprompt="Explain this codebase"
./gradlew opencode-stats -Pdays="7"
./gradlew opencode-auth
./gradlew opencode-version
```

Configure defaults:

```kotlin
opencode {
    model = "anthropic/claude-sonnet-4-20250514"
    thinking = true
    format = "json"
}
```

## Building

```bash
./gradlew build
```

## License

MIT -- see [LICENSE](LICENSE).
