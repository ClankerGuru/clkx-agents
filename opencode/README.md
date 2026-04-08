# OpenCode Plugin

[![CLI Version](https://img.shields.io/badge/opencode-1.3.13-blue)](https://github.com/opencode-ai/opencode)

Gradle settings plugin wrapping the [OpenCode](https://github.com/opencode-ai/opencode) CLI.

## Install the CLI

```bash
# macOS (Homebrew)
brew install opencode

# bun
bun install -g opencode-ai

# npm
npm install -g opencode-ai
```

## Plugin

```kotlin
// settings.gradle.kts
plugins {
    id("zone.clanker.opencode") version "0.38.0"
}

opencode {
    model = "anthropic/claude-sonnet-4-20250514"
    thinking = true
    format = "json"
}
```

## Tasks

| Task | Description |
|------|-------------|
| `opencode-run` | Run OpenCode with a prompt |
| `opencode-auth` | List OpenCode providers |
| `opencode-version` | Show OpenCode version |
| `opencode-stats` | Show OpenCode usage stats |
| `opencode-models` | List OpenCode models |
| `opencode-export` | Export OpenCode session |
| `opencode-import` | Import OpenCode session |
| `opencode-mcp-list` | List OpenCode MCP servers |
| `opencode-mcp-add` | Add an OpenCode MCP server |
| `opencode-session-list` | List OpenCode sessions |
| `opencode-session-delete` | Delete an OpenCode session |
| `opencode-agent-list` | List OpenCode agents |
| `opencode-agent-create` | Create an OpenCode agent |
| `opencode-serve` | Start OpenCode server |
| `opencode-upgrade` | Upgrade OpenCode |
| `opencode-pr` | Review a pull request with OpenCode |
| `opencode-github-install` | Install OpenCode GitHub integration |
| `opencode-github-run` | Run OpenCode GitHub action |

### opencode-run

```bash
./gradlew opencode-run -Pprompt="Explain this codebase"
./gradlew opencode-run -Pprompt="Add tests" -Pmodel=anthropic/claude-sonnet-4-20250514
```

### opencode-auth

```bash
./gradlew opencode-auth
```

### opencode-version

```bash
./gradlew opencode-version
```

### opencode-stats

```bash
./gradlew opencode-stats
./gradlew opencode-stats -Pdays="7"
./gradlew opencode-stats -Ptools="true" -Pmodels="true"
./gradlew opencode-stats -PstatsProject="my-project"
```

### opencode-models

```bash
./gradlew opencode-models
./gradlew opencode-models -Pprovider="anthropic"
```

### opencode-export

```bash
./gradlew opencode-export
./gradlew opencode-export -PsessionId="abc123"
```

### opencode-import

```bash
./gradlew opencode-import -Pfile="session.json"
```

### opencode-mcp-list

```bash
./gradlew opencode-mcp-list
```

### opencode-mcp-add

```bash
./gradlew opencode-mcp-add
```

### opencode-session-list

```bash
./gradlew opencode-session-list
```

### opencode-session-delete

```bash
./gradlew opencode-session-delete -PsessionId="abc123"
```

### opencode-agent-list

```bash
./gradlew opencode-agent-list
```

### opencode-agent-create

```bash
./gradlew opencode-agent-create
```

### opencode-serve

```bash
./gradlew opencode-serve
```

### opencode-upgrade

```bash
./gradlew opencode-upgrade
./gradlew opencode-upgrade -Ptarget="1.4.0"
```

### opencode-pr

```bash
./gradlew opencode-pr -Pnumber="42"
```

### opencode-github-install

```bash
./gradlew opencode-github-install
```

### opencode-github-run

```bash
./gradlew opencode-github-run
```

## Configuration

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `model` | `String` | `""` | Model to use (e.g. `"anthropic/claude-sonnet-4-20250514"`) |
| `format` | `String` | `""` | Output format (`"json"`, `"text"`) |
| `agent` | `String` | `""` | Agent to use |
| `variant` | `String` | `""` | Agent variant |
| `thinking` | `Boolean` | `false` | Enable thinking/reasoning mode |
| `file` | `List<String>` | `[]` | Files to include as context |
| `dir` | `String` | `""` | Working directory override |
| `share` | `Boolean` | `false` | Share the session |
| `extraArgs` | `List<String>` | `[]` | Additional CLI arguments |

## `opencode-run` Options

All configuration properties above are passed to `opencode-run`. Additionally:

| Property | Flag | Required | Description |
|----------|------|----------|-------------|
| `-Pprompt` | `--prompt` | Yes | The prompt to send |
