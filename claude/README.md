# Claude Code Plugin

[![CLI Version](https://img.shields.io/badge/claude--code-2.1.84-blue)](https://docs.anthropic.com/en/docs/claude-code)

Gradle settings plugin wrapping the [Claude Code](https://docs.anthropic.com/en/docs/claude-code) CLI.

## Install the CLI

```bash
npm install -g @anthropic-ai/claude-code
```

## Plugin

```kotlin
// settings.gradle.kts
plugins {
    id("zone.clanker.claude") version "0.38.0"
}

claude {
    model = "opus"
    effort = "high"
}
```

## Tasks

| Task | Description |
|------|-------------|
| `claude-run` | Run Claude Code with a prompt |
| `claude-resume` | Resume a Claude Code session |
| `claude-auth` | Check Claude Code auth status |
| `claude-version` | Show Claude Code version |
| `claude-doctor` | Run Claude Code doctor |
| `claude-agents` | List configured Claude agents |
| `claude-mcp-list` | List Claude MCP servers |
| `claude-mcp-add` | Add a Claude MCP server |
| `claude-mcp-remove` | Remove a Claude MCP server |
| `claude-mcp-serve` | Start Claude MCP server |
| `claude-update` | Update Claude Code |
| `claude-install` | Install Claude Code |
| `claude-setup-token` | Set up Claude API token |

### claude-run

```bash
./gradlew claude-run -Pprompt="Fix the failing tests"
./gradlew claude-run -Pprompt="Add error handling" -Pmodel=sonnet
```

### claude-resume

```bash
./gradlew claude-resume -PsessionId="abc123"
./gradlew claude-resume -PsessionId="abc123" -Pprompt="Continue with tests"
./gradlew claude-resume -PsessionId="abc123" -PforkSession=true
```

### claude-auth

```bash
./gradlew claude-auth
```

### claude-version

```bash
./gradlew claude-version
```

### claude-doctor

```bash
./gradlew claude-doctor
```

### claude-agents

```bash
./gradlew claude-agents
```

### claude-mcp-list

```bash
./gradlew claude-mcp-list
```

### claude-mcp-add

```bash
./gradlew claude-mcp-add -Pname="my-server" -Ptransport="stdio"
```

### claude-mcp-remove

```bash
./gradlew claude-mcp-remove -Pname="my-server"
```

### claude-mcp-serve

```bash
./gradlew claude-mcp-serve
```

### claude-update

```bash
./gradlew claude-update
```

### claude-install

```bash
./gradlew claude-install
./gradlew claude-install -Ptarget="global"
```

### claude-setup-token

```bash
./gradlew claude-setup-token
```

## Configuration

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `model` | `String` | `""` | Model to use (e.g. `"opus"`, `"sonnet"`, `"claude-sonnet-4-20250514"`) |
| `outputFormat` | `String` | `"text"` | Output format (`"text"`, `"json"`, `"stream-json"`) |
| `permissionMode` | `String` | `"default"` | Permission mode (`"default"`, `"bypassPermissions"`) |
| `maxBudgetUsd` | `Double` | `0.0` | Max budget in USD (0 = unlimited) |
| `systemPrompt` | `String` | `""` | System prompt to prepend |
| `allowedTools` | `List<String>` | `[]` | Tools to allow |
| `disallowedTools` | `List<String>` | `[]` | Tools to disallow |
| `effort` | `String` | `""` | Effort level (`"low"`, `"medium"`, `"high"`) |
| `extraArgs` | `List<String>` | `[]` | Additional CLI arguments |

## `claude-run` Options

All configuration properties above are passed to `claude-run`. Additionally:

| Property | Flag | Required | Description |
|----------|------|----------|-------------|
| `-Pprompt` | `-p` | Yes | The prompt to send |
| `-Pmodel` | `--model` | No | Override the configured model |
