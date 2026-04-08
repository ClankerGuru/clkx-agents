# GitHub Copilot Plugin

[![CLI Version](https://img.shields.io/badge/copilot--cli-1.0.15-blue)](https://github.com/github/copilot-cli)

Gradle settings plugin wrapping the [Copilot CLI](https://github.com/github/copilot-cli) -- the standalone GitHub Copilot command-line tool.

## Install the CLI

```bash
# macOS (preferred)
brew install --cask copilot-cli

# Or download from GitHub releases
# https://github.com/github/copilot-cli/releases
```

Copilot CLI is a standalone desktop application. It is **not** an npm package and **not** a `gh` extension. The binary is `copilot`. Authenticate with:

```bash
copilot login
```

## Plugin

```kotlin
// settings.gradle.kts
plugins {
    id("zone.clanker.copilot") version "0.38.0"
}

copilot {
    model = "gpt-4o"
    autopilot = true
    allowAll = true
}
```

## Tasks

| Task | Description |
|------|-------------|
| `copilot-run` | Run GitHub Copilot with a prompt |
| `copilot-resume` | Resume a Copilot session |
| `copilot-init` | Initialize Copilot in the project |
| `copilot-auth` | Log in to GitHub Copilot |
| `copilot-version` | Show Copilot version |
| `copilot-update` | Update Copilot |
| `copilot-plugin-list` | List Copilot plugins |
| `copilot-plugin-install` | Install a Copilot plugin |
| `copilot-plugin-uninstall` | Uninstall a Copilot plugin |

### copilot-run

```bash
./gradlew copilot-run -Pprompt="Refactor the data layer"
./gradlew copilot-run -Pprompt="Add tests" -Pmodel=gpt-4o
```

### copilot-resume

```bash
./gradlew copilot-resume -PsessionId="abc123"
```

### copilot-init

```bash
./gradlew copilot-init
```

### copilot-auth

```bash
./gradlew copilot-auth
```

### copilot-version

```bash
./gradlew copilot-version
```

### copilot-update

```bash
./gradlew copilot-update
```

### copilot-plugin-list

```bash
./gradlew copilot-plugin-list
```

### copilot-plugin-install

```bash
./gradlew copilot-plugin-install -Psource="https://example.com/plugin.wasm"
```

### copilot-plugin-uninstall

```bash
./gradlew copilot-plugin-uninstall -Pname="my-plugin"
```

## Configuration

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `model` | `String` | `""` | Model to use (e.g. `"gpt-4o"`, `"claude-3.5-sonnet"`) |
| `effort` | `String` | `""` | Effort level |
| `outputFormat` | `String` | `""` | Output format |
| `allowAll` | `Boolean` | `false` | Allow all permissions |
| `allowAllTools` | `Boolean` | `false` | Allow all tools |
| `allowAllPaths` | `Boolean` | `false` | Allow all file paths |
| `autopilot` | `Boolean` | `false` | Run in autopilot mode |
| `silent` | `Boolean` | `false` | Suppress interactive output |
| `agent` | `String` | `""` | Agent to use |
| `addDir` | `List<String>` | `[]` | Additional directories to include |
| `configDir` | `String` | `""` | Custom config directory |
| `extraArgs` | `List<String>` | `[]` | Additional CLI arguments |

## `copilot-run` Options

All configuration properties above are passed to `copilot-run`. Additionally:

| Property | Flag | Required | Description |
|----------|------|----------|-------------|
| `-Pprompt` | `-m` | Yes | The prompt to send |
