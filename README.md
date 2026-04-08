# clkx-agents

[![Build](https://github.com/ClankerGuru/clkx-agents/actions/workflows/build.yml/badge.svg)](https://github.com/ClankerGuru/clkx-agents/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/zone.clanker/clkx-agents)](https://central.sonatype.com/namespace/zone.clanker)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Gradle settings plugins for AI coding agents -- Claude Code, GitHub Copilot, OpenAI Codex, and OpenCode.

## Modules

| Module | Plugin ID | Tasks | README |
|--------|-----------|-------|--------|
| [`:exec`](exec/) | -- | -- | [exec/README.md](exec/README.md) |
| [`:claude`](claude/) | `zone.clanker.claude` | 13 | [claude/README.md](claude/README.md) |
| [`:copilot`](copilot/) | `zone.clanker.copilot` | 9 | [copilot/README.md](copilot/README.md) |
| [`:codex`](codex/) | `zone.clanker.codex` | 13 | [codex/README.md](codex/README.md) |
| [`:opencode`](opencode/) | `zone.clanker.opencode` | 18 | [opencode/README.md](opencode/README.md) |

## Install the CLIs

```bash
# Claude Code
bun install -g @anthropic-ai/claude-code

# GitHub Copilot
brew install --cask copilot-cli      # or: bun install -g @github/copilot

# OpenAI Codex
brew install --cask codex            # or: bun install -g @openai/codex

# OpenCode
brew install opencode                # or: bun install -g opencode-ai
```

## Quick Start

Apply the plugin for the agent you want to use in `settings.gradle.kts`:

```kotlin
plugins {
    id("zone.clanker.claude") version "<version>"
}

claude {
    model = "opus"
    effort = "high"
}
```

Then run tasks from the command line:

```bash
./gradlew claude-run -Pprompt="Fix the build"
```

### Claude Code

```bash
./gradlew claude-run -Pprompt="Fix the build"
./gradlew claude-resume -PsessionId="abc123"
./gradlew claude-auth
./gradlew claude-version
./gradlew claude-doctor
```

See [claude/README.md](claude/README.md) for all 13 tasks and configuration options.

### GitHub Copilot

```bash
./gradlew copilot-run -Pprompt="Refactor the data layer"
./gradlew copilot-resume -PsessionId="abc123"
./gradlew copilot-init
./gradlew copilot-auth
./gradlew copilot-version
```

See [copilot/README.md](copilot/README.md) for all 9 tasks and configuration options.

### OpenAI Codex

```bash
./gradlew codex-exec -Pprompt="Add unit tests"
./gradlew codex-review -Pbase="main"
./gradlew codex-auth
./gradlew codex-version
```

See [codex/README.md](codex/README.md) for all 13 tasks and configuration options.

### OpenCode

```bash
./gradlew opencode-run -Pprompt="Explain this codebase"
./gradlew opencode-stats -Pdays="7"
./gradlew opencode-auth
./gradlew opencode-version
```

See [opencode/README.md](opencode/README.md) for all 18 tasks and configuration options.

## Building

```bash
./gradlew build
```

## License

MIT -- see [LICENSE](LICENSE).
