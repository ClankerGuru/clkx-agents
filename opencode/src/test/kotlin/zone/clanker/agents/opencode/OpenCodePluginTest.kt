package zone.clanker.agents.opencode

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
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
                ext.extraArgs shouldBe emptyList()
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
            }

            then("tasks have correct group") {
                project.tasks.findByName(OpenCode.TASK_RUN)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_AUTH)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_VERSION)!!.group shouldBe OpenCode.GROUP
                project.tasks.findByName(OpenCode.TASK_STATS)!!.group shouldBe OpenCode.GROUP
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
