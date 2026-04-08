package zone.clanker.agents.claude

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.gradle.testfixtures.ProjectBuilder

class ClaudePluginTest :
    BehaviorSpec({
        given("Claude constants") {
            then("GROUP is claude") {
                Claude.GROUP shouldBe "claude"
            }
            then("EXTENSION_NAME is claude") {
                Claude.EXTENSION_NAME shouldBe "claude"
            }
            then("task names are correct") {
                Claude.TASK_RUN shouldBe "claude-run"
                Claude.TASK_RESUME shouldBe "claude-resume"
                Claude.TASK_AUTH shouldBe "claude-auth"
                Claude.TASK_VERSION shouldBe "claude-version"
                Claude.TASK_DOCTOR shouldBe "claude-doctor"
                Claude.TASK_AGENTS shouldBe "claude-agents"
                Claude.TASK_MCP_LIST shouldBe "claude-mcp-list"
                Claude.TASK_MCP_ADD shouldBe "claude-mcp-add"
                Claude.TASK_MCP_REMOVE shouldBe "claude-mcp-remove"
                Claude.TASK_MCP_SERVE shouldBe "claude-mcp-serve"
                Claude.TASK_UPDATE shouldBe "claude-update"
                Claude.TASK_INSTALL shouldBe "claude-install"
                Claude.TASK_SETUP_TOKEN shouldBe "claude-setup-token"
            }
        }

        given("Claude.SettingsExtension") {
            val ext = Claude.SettingsExtension()

            `when`("created with defaults") {
                then("it has correct defaults") {
                    ext.model shouldBe ""
                    ext.outputFormat shouldBe "text"
                    ext.permissionMode shouldBe "default"
                    ext.maxBudgetUsd shouldBe 0.0
                    ext.systemPrompt shouldBe ""
                    ext.allowedTools shouldBe emptyList()
                    ext.disallowedTools shouldBe emptyList()
                    ext.effort shouldBe ""
                    ext.bare shouldBe false
                    ext.dangerouslySkipPermissions shouldBe false
                    ext.verbose shouldBe false
                    ext.addDir shouldBe emptyList()
                    ext.appendSystemPrompt shouldBe ""
                    ext.extraArgs shouldBe emptyList()
                }
            }

            `when`("properties are set") {
                then("model is mutable") {
                    ext.model = "opus"
                    ext.model shouldBe "opus"
                }
                then("outputFormat is mutable") {
                    ext.outputFormat = "json"
                    ext.outputFormat shouldBe "json"
                }
                then("permissionMode is mutable") {
                    ext.permissionMode = "bypassPermissions"
                    ext.permissionMode shouldBe "bypassPermissions"
                }
                then("maxBudgetUsd is mutable") {
                    ext.maxBudgetUsd = 5.0
                    ext.maxBudgetUsd shouldBe 5.0
                }
                then("systemPrompt is mutable") {
                    ext.systemPrompt = "You are a helpful assistant"
                    ext.systemPrompt shouldBe "You are a helpful assistant"
                }
                then("allowedTools is mutable") {
                    ext.allowedTools = listOf("Read", "Write")
                    ext.allowedTools shouldBe listOf("Read", "Write")
                }
                then("disallowedTools is mutable") {
                    ext.disallowedTools = listOf("Bash")
                    ext.disallowedTools shouldBe listOf("Bash")
                }
                then("effort is mutable") {
                    ext.effort = "high"
                    ext.effort shouldBe "high"
                }
                then("bare is mutable") {
                    ext.bare = true
                    ext.bare shouldBe true
                }
                then("dangerouslySkipPermissions is mutable") {
                    ext.dangerouslySkipPermissions = true
                    ext.dangerouslySkipPermissions shouldBe true
                }
                then("verbose is mutable") {
                    ext.verbose = true
                    ext.verbose shouldBe true
                }
                then("addDir is mutable") {
                    ext.addDir = listOf("/tmp/dir1", "/tmp/dir2")
                    ext.addDir shouldBe listOf("/tmp/dir1", "/tmp/dir2")
                }
                then("appendSystemPrompt is mutable") {
                    ext.appendSystemPrompt = "Be concise"
                    ext.appendSystemPrompt shouldBe "Be concise"
                }
                then("extraArgs is mutable") {
                    ext.extraArgs = listOf("--verbose")
                    ext.extraArgs shouldBe listOf("--verbose")
                }
            }
        }

        given("Claude.registerTasks") {
            val project = ProjectBuilder.builder().build()
            val ext = project.extensions.create(Claude.EXTENSION_NAME, Claude.SettingsExtension::class.java)
            Claude.registerTasks(project, ext)

            `when`("tasks are registered") {
                then("all tasks exist") {
                    project.tasks.findByName(Claude.TASK_RUN).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_RESUME).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_AUTH).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_VERSION).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_DOCTOR).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_AGENTS).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_MCP_LIST).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_MCP_ADD).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_MCP_REMOVE).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_MCP_SERVE).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_UPDATE).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_INSTALL).shouldNotBeNull()
                    project.tasks.findByName(Claude.TASK_SETUP_TOKEN).shouldNotBeNull()
                }

                then("tasks have correct group") {
                    project.tasks.findByName(Claude.TASK_RUN)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_RESUME)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_AUTH)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_VERSION)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_DOCTOR)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_AGENTS)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_MCP_LIST)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_MCP_ADD)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_MCP_REMOVE)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_MCP_SERVE)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_UPDATE)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_INSTALL)!!.group shouldBe Claude.GROUP
                    project.tasks.findByName(Claude.TASK_SETUP_TOKEN)!!.group shouldBe Claude.GROUP
                }

                then("tasks have correct descriptions") {
                    project.tasks.findByName(Claude.TASK_RUN)!!.description shouldBe "Run Claude Code with a prompt"
                    project.tasks.findByName(Claude.TASK_RESUME)!!.description shouldBe "Resume a Claude Code session"
                    project.tasks.findByName(Claude.TASK_AUTH)!!.description shouldBe "Check Claude Code auth status"
                    project.tasks.findByName(Claude.TASK_VERSION)!!.description shouldBe "Show Claude Code version"
                    project.tasks.findByName(Claude.TASK_DOCTOR)!!.description shouldBe "Run Claude Code doctor"
                    project.tasks.findByName(Claude.TASK_AGENTS)!!.description shouldBe "List configured Claude agents"
                    project.tasks.findByName(Claude.TASK_MCP_LIST)!!.description shouldBe "List Claude MCP servers"
                    project.tasks.findByName(Claude.TASK_MCP_ADD)!!.description shouldBe "Add a Claude MCP server"
                    project.tasks.findByName(Claude.TASK_MCP_REMOVE)!!.description shouldBe "Remove a Claude MCP server"
                    project.tasks.findByName(Claude.TASK_MCP_SERVE)!!.description shouldBe "Start Claude MCP server"
                    project.tasks.findByName(Claude.TASK_UPDATE)!!.description shouldBe "Update Claude Code"
                    project.tasks.findByName(Claude.TASK_INSTALL)!!.description shouldBe "Install Claude Code"
                    project.tasks.findByName(Claude.TASK_SETUP_TOKEN)!!.description shouldBe "Set up Claude API token"
                }

                then("tasks have correct types") {
                    project.tasks.findByName(Claude.TASK_RUN)!!.shouldBeInstanceOf<ClaudeRunTask>()
                    project.tasks.findByName(Claude.TASK_RESUME)!!.shouldBeInstanceOf<ClaudeResumeTask>()
                    project.tasks.findByName(Claude.TASK_AUTH)!!.shouldBeInstanceOf<ClaudeAuthTask>()
                    project.tasks.findByName(Claude.TASK_VERSION)!!.shouldBeInstanceOf<ClaudeVersionTask>()
                    project.tasks.findByName(Claude.TASK_DOCTOR)!!.shouldBeInstanceOf<ClaudeDoctorTask>()
                    project.tasks.findByName(Claude.TASK_AGENTS)!!.shouldBeInstanceOf<ClaudeAgentsTask>()
                    project.tasks.findByName(Claude.TASK_MCP_LIST)!!.shouldBeInstanceOf<ClaudeMcpListTask>()
                    project.tasks.findByName(Claude.TASK_MCP_ADD)!!.shouldBeInstanceOf<ClaudeMcpAddTask>()
                    project.tasks.findByName(Claude.TASK_MCP_REMOVE)!!.shouldBeInstanceOf<ClaudeMcpRemoveTask>()
                    project.tasks.findByName(Claude.TASK_MCP_SERVE)!!.shouldBeInstanceOf<ClaudeMcpServeTask>()
                    project.tasks.findByName(Claude.TASK_UPDATE)!!.shouldBeInstanceOf<ClaudeUpdateTask>()
                    project.tasks.findByName(Claude.TASK_INSTALL)!!.shouldBeInstanceOf<ClaudeInstallTask>()
                    project.tasks.findByName(Claude.TASK_SETUP_TOKEN)!!.shouldBeInstanceOf<ClaudeSetupTokenTask>()
                }

                then("run task has extension set") {
                    val runTask = project.tasks.findByName(Claude.TASK_RUN)!! as ClaudeRunTask
                    runTask.extension shouldBe ext
                }

                then("resume task has extension set") {
                    val resumeTask = project.tasks.findByName(Claude.TASK_RESUME)!! as ClaudeResumeTask
                    resumeTask.extension shouldBe ext
                }

                then("extension is registered") {
                    project.extensions.findByType(Claude.SettingsExtension::class.java).shouldNotBeNull()
                }
            }
        }

        given("Claude.SettingsPlugin") {
            `when`("instantiated") {
                then("it is not null") {
                    val plugin = Claude.SettingsPlugin()
                    plugin.shouldNotBeNull()
                }
            }
        }

        given("Claude architecture") {
            `when`("checking data object structure") {
                then("Claude is a data object") {
                    Claude::class.isData shouldBe true
                    Claude::class.objectInstance.shouldNotBeNull()
                }
                then("SettingsPlugin is inside Claude") {
                    Claude.SettingsPlugin::class.java.enclosingClass shouldBe Claude::class.java
                }
                then("SettingsExtension is inside Claude") {
                    Claude.SettingsExtension::class.java.enclosingClass shouldBe Claude::class.java
                }
            }
        }
    })
