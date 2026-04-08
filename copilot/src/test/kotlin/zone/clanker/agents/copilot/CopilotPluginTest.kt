package zone.clanker.agents.copilot

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CopilotPluginTest : BehaviorSpec({
    given("Copilot constants") {
        then("GROUP is copilot") {
            Copilot.GROUP shouldBe "copilot"
        }
        then("EXTENSION_NAME is copilot") {
            Copilot.EXTENSION_NAME shouldBe "copilot"
        }
        then("task names are correct") {
            Copilot.TASK_RUN shouldBe "copilot-run"
            Copilot.TASK_RESUME shouldBe "copilot-resume"
            Copilot.TASK_INIT shouldBe "copilot-init"
            Copilot.TASK_AUTH shouldBe "copilot-auth"
            Copilot.TASK_VERSION shouldBe "copilot-version"
        }
    }

    given("Copilot.SettingsExtension") {
        val ext = Copilot.SettingsExtension()

        `when`("created with defaults") {
            then("it has correct defaults") {
                ext.model shouldBe ""
                ext.effort shouldBe ""
                ext.outputFormat shouldBe ""
                ext.allowAll shouldBe false
                ext.allowAllTools shouldBe false
                ext.allowAllPaths shouldBe false
                ext.autopilot shouldBe false
                ext.silent shouldBe false
                ext.agent shouldBe ""
                ext.addDir shouldBe emptyList()
                ext.configDir shouldBe ""
                ext.extraArgs shouldBe emptyList()
            }
        }
    }

    given("Copilot.registerTasks") {
        val project = ProjectBuilder.builder().build()
        val ext = project.extensions.create(Copilot.EXTENSION_NAME, Copilot.SettingsExtension::class.java)
        Copilot.registerTasks(project, ext)

        `when`("tasks are registered") {
            then("all tasks exist") {
                project.tasks.findByName(Copilot.TASK_RUN).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_RESUME).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_INIT).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_AUTH).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_VERSION).shouldNotBeNull()
            }

            then("tasks have correct group") {
                project.tasks.findByName(Copilot.TASK_RUN)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_RESUME)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_INIT)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_AUTH)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_VERSION)!!.group shouldBe Copilot.GROUP
            }

            then("extension is registered") {
                project.extensions.findByType(Copilot.SettingsExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("Copilot.SettingsPlugin") {
        `when`("instantiated") {
            then("it is not null") {
                val plugin = Copilot.SettingsPlugin()
                plugin.shouldNotBeNull()
            }
        }
    }

    given("Copilot architecture") {
        `when`("checking data object structure") {
            then("Copilot is a data object") {
                Copilot::class.isData shouldBe true
                Copilot::class.objectInstance.shouldNotBeNull()
            }
            then("SettingsPlugin is inside Copilot") {
                Copilot.SettingsPlugin::class.java.enclosingClass shouldBe Copilot::class.java
            }
            then("SettingsExtension is inside Copilot") {
                Copilot.SettingsExtension::class.java.enclosingClass shouldBe Copilot::class.java
            }
        }
    }
})
