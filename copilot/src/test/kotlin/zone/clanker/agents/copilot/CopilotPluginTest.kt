package zone.clanker.agents.copilot

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
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
            Copilot.TASK_UPDATE shouldBe "copilot-update"
            Copilot.TASK_PLUGIN_LIST shouldBe "copilot-plugin-list"
            Copilot.TASK_PLUGIN_INSTALL shouldBe "copilot-plugin-install"
            Copilot.TASK_PLUGIN_UNINSTALL shouldBe "copilot-plugin-uninstall"
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
                ext.yolo shouldBe false
                ext.allowAllUrls shouldBe false
                ext.noAskUser shouldBe false
                ext.noCustomInstructions shouldBe false
                ext.extraArgs shouldBe emptyList()
            }
        }

        `when`("properties are set") {
            then("model is mutable") {
                ext.model = "gpt-4o"
                ext.model shouldBe "gpt-4o"
            }
            then("effort is mutable") {
                ext.effort = "high"
                ext.effort shouldBe "high"
            }
            then("outputFormat is mutable") {
                ext.outputFormat = "json"
                ext.outputFormat shouldBe "json"
            }
            then("allowAll is mutable") {
                ext.allowAll = true
                ext.allowAll shouldBe true
            }
            then("allowAllTools is mutable") {
                ext.allowAllTools = true
                ext.allowAllTools shouldBe true
            }
            then("allowAllPaths is mutable") {
                ext.allowAllPaths = true
                ext.allowAllPaths shouldBe true
            }
            then("autopilot is mutable") {
                ext.autopilot = true
                ext.autopilot shouldBe true
            }
            then("silent is mutable") {
                ext.silent = true
                ext.silent shouldBe true
            }
            then("agent is mutable") {
                ext.agent = "my-agent"
                ext.agent shouldBe "my-agent"
            }
            then("addDir is mutable") {
                ext.addDir = listOf("/tmp/dir1", "/tmp/dir2")
                ext.addDir shouldBe listOf("/tmp/dir1", "/tmp/dir2")
            }
            then("configDir is mutable") {
                ext.configDir = "/tmp/config"
                ext.configDir shouldBe "/tmp/config"
            }
            then("yolo is mutable") {
                ext.yolo = true
                ext.yolo shouldBe true
            }
            then("allowAllUrls is mutable") {
                ext.allowAllUrls = true
                ext.allowAllUrls shouldBe true
            }
            then("noAskUser is mutable") {
                ext.noAskUser = true
                ext.noAskUser shouldBe true
            }
            then("noCustomInstructions is mutable") {
                ext.noCustomInstructions = true
                ext.noCustomInstructions shouldBe true
            }
            then("extraArgs is mutable") {
                ext.extraArgs = listOf("--verbose")
                ext.extraArgs shouldBe listOf("--verbose")
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
                project.tasks.findByName(Copilot.TASK_UPDATE).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_PLUGIN_LIST).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_PLUGIN_INSTALL).shouldNotBeNull()
                project.tasks.findByName(Copilot.TASK_PLUGIN_UNINSTALL).shouldNotBeNull()
            }

            then("tasks have correct group") {
                project.tasks.findByName(Copilot.TASK_RUN)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_RESUME)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_INIT)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_AUTH)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_VERSION)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_UPDATE)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_PLUGIN_LIST)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_PLUGIN_INSTALL)!!.group shouldBe Copilot.GROUP
                project.tasks.findByName(Copilot.TASK_PLUGIN_UNINSTALL)!!.group shouldBe Copilot.GROUP
            }

            then("tasks have correct descriptions") {
                project.tasks.findByName(Copilot.TASK_RUN)!!.description shouldBe "Run GitHub Copilot with a prompt"
                project.tasks.findByName(Copilot.TASK_RESUME)!!.description shouldBe "Resume a Copilot session"
                project.tasks.findByName(Copilot.TASK_INIT)!!.description shouldBe "Initialize Copilot in the project"
                project.tasks.findByName(Copilot.TASK_AUTH)!!.description shouldBe "Log in to GitHub Copilot"
                project.tasks.findByName(Copilot.TASK_VERSION)!!.description shouldBe "Show Copilot version"
                project.tasks.findByName(Copilot.TASK_UPDATE)!!.description shouldBe "Update Copilot"
                project.tasks.findByName(Copilot.TASK_PLUGIN_LIST)!!.description shouldBe "List Copilot plugins"
                val installTask = project.tasks.findByName(Copilot.TASK_PLUGIN_INSTALL)!!
                installTask.description shouldBe "Install a Copilot plugin"
                val uninstallTask = project.tasks.findByName(Copilot.TASK_PLUGIN_UNINSTALL)!!
                uninstallTask.description shouldBe "Uninstall a Copilot plugin"
            }

            then("tasks have correct types") {
                project.tasks.findByName(Copilot.TASK_RUN)!!.shouldBeInstanceOf<CopilotRunTask>()
                project.tasks.findByName(Copilot.TASK_RESUME)!!.shouldBeInstanceOf<CopilotResumeTask>()
                project.tasks.findByName(Copilot.TASK_INIT)!!.shouldBeInstanceOf<CopilotInitTask>()
                project.tasks.findByName(Copilot.TASK_AUTH)!!.shouldBeInstanceOf<CopilotAuthTask>()
                project.tasks.findByName(Copilot.TASK_VERSION)!!.shouldBeInstanceOf<CopilotVersionTask>()
                project.tasks.findByName(Copilot.TASK_UPDATE)!!.shouldBeInstanceOf<CopilotUpdateTask>()
                project.tasks.findByName(Copilot.TASK_PLUGIN_LIST)!!.shouldBeInstanceOf<CopilotPluginListTask>()
                val piTask = project.tasks.findByName(Copilot.TASK_PLUGIN_INSTALL)!!
                piTask.shouldBeInstanceOf<CopilotPluginInstallTask>()
                val puTask = project.tasks.findByName(Copilot.TASK_PLUGIN_UNINSTALL)!!
                puTask.shouldBeInstanceOf<CopilotPluginUninstallTask>()
            }

            then("run task has extension set") {
                val runTask = project.tasks.findByName(Copilot.TASK_RUN)!! as CopilotRunTask
                runTask.extension shouldBe ext
            }

            then("resume task has extension set") {
                val resumeTask = project.tasks.findByName(Copilot.TASK_RESUME)!! as CopilotResumeTask
                resumeTask.extension shouldBe ext
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
