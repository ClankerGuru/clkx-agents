package zone.clanker.agents.opencode

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.gradle.testfixtures.ProjectBuilder

class OpenCodePluginTest : BehaviorSpec({
    given("OpenCode constants") {
        then("GROUP is opencode") {
            OpenCode.GROUP shouldBe "opencode"
        }
        then("EXTENSION_NAME is opencode") {
            OpenCode.EXTENSION_NAME shouldBe "opencode"
        }
        then("task names are correct") {
            OpenCode.TASK_RUN shouldBe "opencode-run"
            OpenCode.TASK_AUTH shouldBe "opencode-auth"
            OpenCode.TASK_VERSION shouldBe "opencode-version"
            OpenCode.TASK_STATS shouldBe "opencode-stats"
            OpenCode.TASK_MODELS shouldBe "opencode-models"
            OpenCode.TASK_EXPORT shouldBe "opencode-export"
            OpenCode.TASK_IMPORT shouldBe "opencode-import"
            OpenCode.TASK_MCP_LIST shouldBe "opencode-mcp-list"
            OpenCode.TASK_MCP_ADD shouldBe "opencode-mcp-add"
            OpenCode.TASK_SESSION_LIST shouldBe "opencode-session-list"
            OpenCode.TASK_SESSION_DELETE shouldBe "opencode-session-delete"
            OpenCode.TASK_AGENT_LIST shouldBe "opencode-agent-list"
            OpenCode.TASK_AGENT_CREATE shouldBe "opencode-agent-create"
            OpenCode.TASK_SERVE shouldBe "opencode-serve"
            OpenCode.TASK_UPGRADE shouldBe "opencode-upgrade"
            OpenCode.TASK_PR shouldBe "opencode-pr"
            OpenCode.TASK_GITHUB_INSTALL shouldBe "opencode-github-install"
            OpenCode.TASK_GITHUB_RUN shouldBe "opencode-github-run"
        }
    }

    given("OpenCode.SettingsExtension") {
        val ext = OpenCode.SettingsExtension()

        `when`("created with defaults") {
            then("it has correct defaults") {
                ext.model shouldBe ""
                ext.format shouldBe ""
                ext.agent shouldBe ""
                ext.variant shouldBe ""
                ext.thinking shouldBe false
                ext.file shouldBe emptyList()
                ext.dir shouldBe ""
                ext.share shouldBe false
                ext.pure shouldBe false
                ext.title shouldBe ""
                ext.continueSession shouldBe false
                ext.extraArgs shouldBe emptyList()
            }
        }

        `when`("properties are set") {
            then("model is mutable") {
                ext.model = "anthropic/claude-sonnet-4-20250514"
                ext.model shouldBe "anthropic/claude-sonnet-4-20250514"
            }
            then("format is mutable") {
                ext.format = "json"
                ext.format shouldBe "json"
            }
            then("agent is mutable") {
                ext.agent = "my-agent"
                ext.agent shouldBe "my-agent"
            }
            then("variant is mutable") {
                ext.variant = "fast"
                ext.variant shouldBe "fast"
            }
            then("thinking is mutable") {
                ext.thinking = true
                ext.thinking shouldBe true
            }
            then("file is mutable") {
                ext.file = listOf("file1.kt", "file2.kt")
                ext.file shouldBe listOf("file1.kt", "file2.kt")
            }
            then("dir is mutable") {
                ext.dir = "/tmp/project"
                ext.dir shouldBe "/tmp/project"
            }
            then("share is mutable") {
                ext.share = true
                ext.share shouldBe true
            }
            then("pure is mutable") {
                ext.pure = true
                ext.pure shouldBe true
            }
            then("title is mutable") {
                ext.title = "My Session"
                ext.title shouldBe "My Session"
            }
            then("continueSession is mutable") {
                ext.continueSession = true
                ext.continueSession shouldBe true
            }
            then("extraArgs is mutable") {
                ext.extraArgs = listOf("--verbose")
                ext.extraArgs shouldBe listOf("--verbose")
            }
        }
    }

    given("OpenCode.registerTasks") {
        val project = ProjectBuilder.builder().build()
        val ext = project.extensions.create(OpenCode.EXTENSION_NAME, OpenCode.SettingsExtension::class.java)
        OpenCode.registerTasks(project, ext)

        `when`("tasks are registered") {
            then("all tasks exist") {
                project.tasks.findByName(OpenCode.TASK_RUN).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_AUTH).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_VERSION).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_STATS).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_MODELS).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_EXPORT).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_IMPORT).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_MCP_LIST).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_MCP_ADD).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_SESSION_LIST).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_SESSION_DELETE).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_AGENT_LIST).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_AGENT_CREATE).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_SERVE).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_UPGRADE).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_PR).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_GITHUB_INSTALL).shouldNotBeNull()
                project.tasks.findByName(OpenCode.TASK_GITHUB_RUN).shouldNotBeNull()
            }

            then("tasks have correct group") {
                project.tasks.findByName(OpenCode.TASK_RUN)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_AUTH)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_VERSION)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_STATS)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_MODELS)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_EXPORT)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_IMPORT)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_MCP_LIST)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_MCP_ADD)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_SESSION_LIST)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_SESSION_DELETE)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_AGENT_LIST)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_AGENT_CREATE)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_SERVE)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_UPGRADE)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_PR)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_GITHUB_INSTALL)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_GITHUB_RUN)!!.group shouldBe OpenCode.GROUP
            }

            then("tasks have correct descriptions") {
                project.tasks.findByName(OpenCode.TASK_RUN)!!.description shouldBe "Run OpenCode with a prompt"
                project.tasks.findByName(OpenCode.TASK_AUTH)!!.description shouldBe "List OpenCode providers"
                project.tasks.findByName(OpenCode.TASK_VERSION)!!.description shouldBe "Show OpenCode version"
                project.tasks.findByName(OpenCode.TASK_STATS)!!.description shouldBe "Show OpenCode usage stats"
                project.tasks.findByName(OpenCode.TASK_MODELS)!!.description shouldBe "List OpenCode models"
                project.tasks.findByName(OpenCode.TASK_EXPORT)!!.description shouldBe "Export OpenCode session"
                project.tasks.findByName(OpenCode.TASK_IMPORT)!!.description shouldBe "Import OpenCode session"
                project.tasks.findByName(OpenCode.TASK_MCP_LIST)!!.description shouldBe "List OpenCode MCP servers"
                project.tasks.findByName(OpenCode.TASK_MCP_ADD)!!.description shouldBe "Add an OpenCode MCP server"
                project.tasks.findByName(OpenCode.TASK_SESSION_LIST)!!.description shouldBe "List OpenCode sessions"
                val delTask = project.tasks.findByName(OpenCode.TASK_SESSION_DELETE)!!
                delTask.description shouldBe "Delete an OpenCode session"
                project.tasks.findByName(OpenCode.TASK_AGENT_LIST)!!.description shouldBe "List OpenCode agents"
                project.tasks.findByName(OpenCode.TASK_AGENT_CREATE)!!.description shouldBe "Create an OpenCode agent"
                project.tasks.findByName(OpenCode.TASK_SERVE)!!.description shouldBe "Start OpenCode server"
                project.tasks.findByName(OpenCode.TASK_UPGRADE)!!.description shouldBe "Upgrade OpenCode"
                project.tasks.findByName(OpenCode.TASK_PR)!!.description shouldBe "Review a pull request with OpenCode"
                val ghInstall = project.tasks.findByName(OpenCode.TASK_GITHUB_INSTALL)!!
                ghInstall.description shouldBe "Install OpenCode GitHub integration"
                project.tasks.findByName(OpenCode.TASK_GITHUB_RUN)!!.description shouldBe "Run OpenCode GitHub action"
            }

            then("tasks have correct types") {
                project.tasks.findByName(OpenCode.TASK_RUN)!!.shouldBeInstanceOf<OpenCodeRunTask>()
                project.tasks.findByName(OpenCode.TASK_AUTH)!!.shouldBeInstanceOf<OpenCodeAuthTask>()
                project.tasks.findByName(OpenCode.TASK_VERSION)!!.shouldBeInstanceOf<OpenCodeVersionTask>()
                project.tasks.findByName(OpenCode.TASK_STATS)!!.shouldBeInstanceOf<OpenCodeStatsTask>()
                project.tasks.findByName(OpenCode.TASK_MODELS)!!.shouldBeInstanceOf<OpenCodeModelsTask>()
                project.tasks.findByName(OpenCode.TASK_EXPORT)!!.shouldBeInstanceOf<OpenCodeExportTask>()
                project.tasks.findByName(OpenCode.TASK_IMPORT)!!.shouldBeInstanceOf<OpenCodeImportTask>()
                project.tasks.findByName(OpenCode.TASK_MCP_LIST)!!.shouldBeInstanceOf<OpenCodeMcpListTask>()
                project.tasks.findByName(OpenCode.TASK_MCP_ADD)!!.shouldBeInstanceOf<OpenCodeMcpAddTask>()
                project.tasks.findByName(OpenCode.TASK_SESSION_LIST)!!.shouldBeInstanceOf<OpenCodeSessionListTask>()
                project.tasks.findByName(OpenCode.TASK_SESSION_DELETE)!!.shouldBeInstanceOf<OpenCodeSessionDeleteTask>()
                project.tasks.findByName(OpenCode.TASK_AGENT_LIST)!!.shouldBeInstanceOf<OpenCodeAgentListTask>()
                project.tasks.findByName(OpenCode.TASK_AGENT_CREATE)!!.shouldBeInstanceOf<OpenCodeAgentCreateTask>()
                project.tasks.findByName(OpenCode.TASK_SERVE)!!.shouldBeInstanceOf<OpenCodeServeTask>()
                project.tasks.findByName(OpenCode.TASK_UPGRADE)!!.shouldBeInstanceOf<OpenCodeUpgradeTask>()
                project.tasks.findByName(OpenCode.TASK_PR)!!.shouldBeInstanceOf<OpenCodePrTask>()
                project.tasks.findByName(OpenCode.TASK_GITHUB_INSTALL)!!.shouldBeInstanceOf<OpenCodeGithubInstallTask>()
                project.tasks.findByName(OpenCode.TASK_GITHUB_RUN)!!.shouldBeInstanceOf<OpenCodeGithubRunTask>()
            }

            then("run task has extension set") {
                val runTask = project.tasks.findByName(OpenCode.TASK_RUN)!! as OpenCodeRunTask
                runTask.extension shouldBe ext
            }

            then("extension is registered") {
                project.extensions.findByType(OpenCode.SettingsExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("OpenCode.SettingsPlugin") {
        `when`("instantiated") {
            then("it is not null") {
                val plugin = OpenCode.SettingsPlugin()
                plugin.shouldNotBeNull()
            }
        }
    }

    given("OpenCode architecture") {
        `when`("checking data object structure") {
            then("OpenCode is a data object") {
                OpenCode::class.isData shouldBe true
                OpenCode::class.objectInstance.shouldNotBeNull()
            }
            then("SettingsPlugin is inside OpenCode") {
                OpenCode.SettingsPlugin::class.java.enclosingClass shouldBe OpenCode::class.java
            }
            then("SettingsExtension is inside OpenCode") {
                OpenCode.SettingsExtension::class.java.enclosingClass shouldBe OpenCode::class.java
            }
        }
    }
})
