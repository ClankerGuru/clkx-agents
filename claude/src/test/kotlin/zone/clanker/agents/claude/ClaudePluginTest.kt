package zone.clanker.agents.claude

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class ClaudePluginTest : BehaviorSpec({
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
                ext.extraArgs shouldBe emptyList()
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
            }

            then("tasks have correct group") {
                project.tasks.findByName(Claude.TASK_RUN)!!.group shouldBe Claude.GROUP
                project.tasks.findByName(Claude.TASK_RESUME)!!.group shouldBe Claude.GROUP
                project.tasks.findByName(Claude.TASK_AUTH)!!.group shouldBe Claude.GROUP
                project.tasks.findByName(Claude.TASK_VERSION)!!.group shouldBe Claude.GROUP
                project.tasks.findByName(Claude.TASK_DOCTOR)!!.group shouldBe Claude.GROUP
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
