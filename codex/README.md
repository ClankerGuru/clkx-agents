# OpenAI Codex Plugin

[![CLI Version](https://img.shields.io/badge/codex--cli-0.117.0-blue)](https://github.com/openai/codex)

Gradle settings plugin wrapping the [OpenAI Codex](https://github.com/openai/codex) CLI.

## Install the CLI

```bash
# macOS (Homebrew)
brew install --cask codex

# bun
bun install -g @openai/codex

# npm
npm install -g @openai/codex
```

## Plugin

```kotlin
// settings.gradle.kts
plugins {
    id("zone.clanker.codex") version "0.38.0"
}

codex {
    model = "codex-mini-latest"
    fullAuto = true
    sandbox = "docker"
}
```

## Tasks

| Task | Description |
|------|-------------|
| `codex-exec` | Run OpenAI Codex with a prompt |
| `codex-review` | Run Codex code review |
| `codex-auth` | Check Codex login status |
| `codex-version` | Show Codex version |
| `codex-apply` | Apply latest Codex diff |
| `codex-resume` | Resume a Codex session |
| `codex-fork` | Fork a Codex session |
| `codex-mcp-list` | List Codex MCP servers |
| `codex-mcp-add` | Add a Codex MCP server |
| `codex-mcp-remove` | Remove a Codex MCP server |
| `codex-features` | List Codex features |
| `codex-completion` | Generate Codex shell completions |
| `codex-cloud-list` | List Codex cloud sessions |

### codex-exec

```bash
./gradlew codex-exec -Pprompt="Add unit tests"
./gradlew codex-exec -Pprompt="Fix the build" -Pmodel=o4-mini
```

### codex-review

```bash
./gradlew codex-review -Pbase="main"
./gradlew codex-review -Pcommit="abc123"
./gradlew codex-review -Puncommitted=true
./gradlew codex-review -Pbase="main" -Ptitle="Review data layer changes"
```

### codex-auth

```bash
./gradlew codex-auth
```

### codex-version

```bash
./gradlew codex-version
```

### codex-apply

```bash
./gradlew codex-apply
```

### codex-resume

```bash
./gradlew codex-resume
./gradlew codex-resume -Plast=true
```

### codex-fork

```bash
./gradlew codex-fork
./gradlew codex-fork -Plast=true
```

### codex-mcp-list

```bash
./gradlew codex-mcp-list
```

### codex-mcp-add

```bash
./gradlew codex-mcp-add -PmcpName="my-server"
```

### codex-mcp-remove

```bash
./gradlew codex-mcp-remove -PmcpName="my-server"
```

### codex-features

```bash
./gradlew codex-features
```

### codex-completion

```bash
./gradlew codex-completion -Pshell=bash
./gradlew codex-completion -Pshell=zsh
./gradlew codex-completion -Pshell=fish
```

### codex-cloud-list

```bash
./gradlew codex-cloud-list
```

## Configuration

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `model` | `String` | `""` | Model to use (e.g. `"codex-mini-latest"`, `"o4-mini"`) |
| `sandbox` | `String` | `""` | Sandbox mode (`"docker"`, `"none"`) |
| `approval` | `String` | `""` | Approval mode (`"suggest"`, `"auto-edit"`, `"full-auto"`) |
| `fullAuto` | `Boolean` | `false` | Enable full-auto approval mode |
| `search` | `Boolean` | `false` | Enable search |
| `addDir` | `List<String>` | `[]` | Additional directories to include |
| `outputSchema` | `String` | `""` | JSON schema for structured output |
| `json` | `Boolean` | `false` | Enable JSON output |
| `ephemeral` | `Boolean` | `false` | Ephemeral session (no history) |
| `image` | `List<String>` | `[]` | Image file paths to include |
| `dangerouslyBypass` | `Boolean` | `false` | Bypass all approvals and sandbox restrictions |
| `extraArgs` | `List<String>` | `[]` | Additional CLI arguments |

## `codex-exec` Options

All configuration properties above are passed to `codex-exec`. Additionally:

| Property | Flag | Required | Description |
|----------|------|----------|-------------|
| `-Pprompt` | *(positional)* | Yes | The prompt to send |
