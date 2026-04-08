package zone.clanker.agents.codex

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
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
                ext.extraArgs shouldBe emptyList()
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
            }

            then("tasks have correct group") {
                project.tasks.findByName(Codex.TASK_EXEC)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_REVIEW)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_AUTH)!!.group shouldBe Codex.GROUP
                project.tasks.findByName(Codex.TASK_VERSION)!!.group shouldBe Codex.GROUP
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
