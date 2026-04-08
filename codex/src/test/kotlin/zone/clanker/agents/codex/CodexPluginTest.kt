package zone.clanker.agents.codex

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.gradle.testfixtures.ProjectBuilder

class CodexPluginTest : BehaviorSpec({
    given("Codex constants") {
        then("GROUP is codex") {
            Codex.GROUP shouldBe "codex"
        }
        then("EXTENSION_NAME is codex") {
            Codex.EXTENSION_NAME shouldBe "codex"
        }
        then("task names are correct") {
            Codex.TASK_EXEC shouldBe "codex-exec"
            Codex.TASK_REVIEW shouldBe "codex-review"
            Codex.TASK_AUTH shouldBe "codex-auth"
            Codex.TASK_VERSION shouldBe "codex-version"
            Codex.TASK_APPLY shouldBe "codex-apply"
            Codex.TASK_RESUME shouldBe "codex-resume"
            Codex.TASK_FORK shouldBe "codex-fork"
            Codex.TASK_MCP_LIST shouldBe "codex-mcp-list"
            Codex.TASK_MCP_ADD shouldBe "codex-mcp-add"
            Codex.TASK_MCP_REMOVE shouldBe "codex-mcp-remove"
            Codex.TASK_FEATURES shouldBe "codex-features"
            Codex.TASK_COMPLETION shouldBe "codex-completion"
            Codex.TASK_CLOUD_LIST shouldBe "codex-cloud-list"
        }
    }

    given("Codex.SettingsExtension") {
        val ext = Codex.SettingsExtension()

        `when`("created with defaults") {
            then("it has correct defaults") {
                ext.model shouldBe ""
                ext.sandbox shouldBe ""
                ext.approval shouldBe ""
                ext.fullAuto shouldBe false
                ext.search shouldBe false
                ext.addDir shouldBe emptyList()
                ext.outputSchema shouldBe ""
                ext.json shouldBe false
                ext.ephemeral shouldBe false
                ext.image shouldBe emptyList()
                ext.dangerouslyBypass shouldBe false
                ext.extraArgs shouldBe emptyList()
            }
        }

        `when`("properties are set") {
            then("model is mutable") {
                ext.model = "codex-mini-latest"
                ext.model shouldBe "codex-mini-latest"
            }
            then("sandbox is mutable") {
                ext.sandbox = "docker"
                ext.sandbox shouldBe "docker"
            }
            then("approval is mutable") {
                ext.approval = "full-auto"
                ext.approval shouldBe "full-auto"
            }
            then("fullAuto is mutable") {
                ext.fullAuto = true
                ext.fullAuto shouldBe true
            }
            then("search is mutable") {
                ext.search = true
                ext.search shouldBe true
            }
            then("addDir is mutable") {
                ext.addDir = listOf("/tmp/dir1")
                ext.addDir shouldBe listOf("/tmp/dir1")
            }
            then("outputSchema is mutable") {
                ext.outputSchema = "{}"
                ext.outputSchema shouldBe "{}"
            }
            then("json is mutable") {
                ext.json = true
                ext.json shouldBe true
            }
            then("ephemeral is mutable") {
                ext.ephemeral = true
                ext.ephemeral shouldBe true
            }
            then("image is mutable") {
                ext.image = listOf("image.png")
                ext.image shouldBe listOf("image.png")
            }
            then("dangerouslyBypass is mutable") {
                ext.dangerouslyBypass = true
                ext.dangerouslyBypass shouldBe true
            }
            then("extraArgs is mutable") {
                ext.extraArgs = listOf("--verbose")
                ext.extraArgs shouldBe listOf("--verbose")
            }
        }
    }

    given("Codex.registerTasks") {
        val project = ProjectBuilder.builder().build()
        val ext = project.extensions.create(Codex.EXTENSION_NAME, Codex.SettingsExtension::class.java)
        Codex.registerTasks(project, ext)

        `when`("tasks are registered") {
            then("all tasks exist") {
                project.tasks.findByName(Codex.TASK_EXEC).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_REVIEW).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_AUTH).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_VERSION).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_APPLY).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_RESUME).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_FORK).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_MCP_LIST).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_MCP_ADD).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_MCP_REMOVE).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_FEATURES).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_COMPLETION).shouldNotBeNull()
                project.tasks.findByName(Codex.TASK_CLOUD_LIST).shouldNotBeNull()
            }

            then("tasks have correct group") {
                project.tasks.findByName(Codex.TASK_EXEC)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_REVIEW)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_AUTH)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_VERSION)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_APPLY)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_RESUME)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_FORK)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_MCP_LIST)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_MCP_ADD)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_MCP_REMOVE)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_FEATURES)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_COMPLETION)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_CLOUD_LIST)!!.group shouldBe Codex.GROUP
            }

            then("tasks have correct descriptions") {
                project.tasks.findByName(Codex.TASK_EXEC)!!.description shouldBe "Run OpenAI Codex with a prompt"
                project.tasks.findByName(Codex.TASK_REVIEW)!!.description shouldBe "Run Codex code review"
                project.tasks.findByName(Codex.TASK_AUTH)!!.description shouldBe "Check Codex login status"
                project.tasks.findByName(Codex.TASK_VERSION)!!.description shouldBe "Show Codex version"
                project.tasks.findByName(Codex.TASK_APPLY)!!.description shouldBe "Apply latest Codex diff"
                project.tasks.findByName(Codex.TASK_RESUME)!!.description shouldBe "Resume a Codex session"
                project.tasks.findByName(Codex.TASK_FORK)!!.description shouldBe "Fork a Codex session"
                project.tasks.findByName(Codex.TASK_MCP_LIST)!!.description shouldBe "List Codex MCP servers"
                project.tasks.findByName(Codex.TASK_MCP_ADD)!!.description shouldBe "Add a Codex MCP server"
                project.tasks.findByName(Codex.TASK_MCP_REMOVE)!!.description shouldBe "Remove a Codex MCP server"
                project.tasks.findByName(Codex.TASK_FEATURES)!!.description shouldBe "List Codex features"
                val completionTask = project.tasks.findByName(Codex.TASK_COMPLETION)!!
                completionTask.description shouldBe "Generate Codex shell completions"
                project.tasks.findByName(Codex.TASK_CLOUD_LIST)!!.description shouldBe "List Codex cloud sessions"
            }

            then("tasks have correct types") {
                project.tasks.findByName(Codex.TASK_EXEC)!!.shouldBeInstanceOf<CodexExecTask>()
                project.tasks.findByName(Codex.TASK_REVIEW)!!.shouldBeInstanceOf<CodexReviewTask>()
                project.tasks.findByName(Codex.TASK_AUTH)!!.shouldBeInstanceOf<CodexAuthTask>()
                project.tasks.findByName(Codex.TASK_VERSION)!!.shouldBeInstanceOf<CodexVersionTask>()
                project.tasks.findByName(Codex.TASK_APPLY)!!.shouldBeInstanceOf<CodexApplyTask>()
                project.tasks.findByName(Codex.TASK_RESUME)!!.shouldBeInstanceOf<CodexResumeTask>()
                project.tasks.findByName(Codex.TASK_FORK)!!.shouldBeInstanceOf<CodexForkTask>()
                project.tasks.findByName(Codex.TASK_MCP_LIST)!!.shouldBeInstanceOf<CodexMcpListTask>()
                project.tasks.findByName(Codex.TASK_MCP_ADD)!!.shouldBeInstanceOf<CodexMcpAddTask>()
                project.tasks.findByName(Codex.TASK_MCP_REMOVE)!!.shouldBeInstanceOf<CodexMcpRemoveTask>()
                project.tasks.findByName(Codex.TASK_FEATURES)!!.shouldBeInstanceOf<CodexFeaturesTask>()
                project.tasks.findByName(Codex.TASK_COMPLETION)!!.shouldBeInstanceOf<CodexCompletionTask>()
                project.tasks.findByName(Codex.TASK_CLOUD_LIST)!!.shouldBeInstanceOf<CodexCloudListTask>()
            }

            then("exec task has extension set") {
                val execTask = project.tasks.findByName(Codex.TASK_EXEC)!! as CodexExecTask
                execTask.extension shouldBe ext
            }

            then("review task has extension set") {
                val reviewTask = project.tasks.findByName(Codex.TASK_REVIEW)!! as CodexReviewTask
                reviewTask.extension shouldBe ext
            }

            then("extension is registered") {
                project.extensions.findByType(Codex.SettingsExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("Codex.SettingsPlugin") {
        `when`("instantiated") {
            then("it is not null") {
                val plugin = Codex.SettingsPlugin()
                plugin.shouldNotBeNull()
            }
        }
    }

    given("Codex architecture") {
        `when`("checking data object structure") {
            then("Codex is a data object") {
                Codex::class.isData shouldBe true
                Codex::class.objectInstance.shouldNotBeNull()
            }
            then("SettingsPlugin is inside Codex") {
                Codex.SettingsPlugin::class.java.enclosingClass shouldBe Codex::class.java
            }
            then("SettingsExtension is inside Codex") {
                Codex.SettingsExtension::class.java.enclosingClass shouldBe Codex::class.java
            }
        }
    }
})
