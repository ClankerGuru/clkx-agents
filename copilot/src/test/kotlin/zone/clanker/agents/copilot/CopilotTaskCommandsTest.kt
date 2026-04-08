package zone.clanker.agents.copilot

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CopilotTaskCommandsTest :
    BehaviorSpec({
        given("CopilotInitTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val task = project.tasks.create("test-init", CopilotInitTask::class.java)

            then("it returns copilot init") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("init")
            }
        }

        given("CopilotAuthTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val task = project.tasks.create("test-auth", CopilotAuthTask::class.java)

            then("it returns copilot login") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("login")
            }
        }

        given("CopilotVersionTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val task = project.tasks.create("test-version", CopilotVersionTask::class.java)

            then("it returns copilot version") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("version")
            }
        }

        given("CopilotUpdateTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val task = project.tasks.create("test-update", CopilotUpdateTask::class.java)

            then("it returns copilot update") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("update")
            }
        }

        given("CopilotPluginListTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val task = project.tasks.create("test-plugin-list", CopilotPluginListTask::class.java)

            then("it returns copilot plugin list") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("plugin", "list")
            }
        }

        given("CopilotPluginInstallTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            project.extensions.extraProperties["source"] = "github/plugin"
            val task = project.tasks.create("test-plugin-install", CopilotPluginInstallTask::class.java)

            then("it returns copilot plugin install with source") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("plugin", "install", "github/plugin")
            }
        }

        given("CopilotPluginInstallTask.buildCommand without source") {
            val project = ProjectBuilder.builder().build()
            val task = project.tasks.create("test-plugin-install-fail", CopilotPluginInstallTask::class.java)

            then("it throws when source is missing") {
                shouldThrow<IllegalStateException> {
                    task.buildCommand()
                }
            }
        }

        given("CopilotPluginUninstallTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            project.extensions.extraProperties["pluginName"] = "my-plugin"
            val task = project.tasks.create("test-plugin-uninstall", CopilotPluginUninstallTask::class.java)

            then("it returns copilot plugin uninstall with name") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args shouldBe listOf("plugin", "uninstall", "my-plugin")
            }
        }

        given("CopilotResumeTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val ext = Copilot.SettingsExtension()
            project.extensions.extraProperties["sessionId"] = "sess-456"
            val task = project.tasks.create("test-resume", CopilotResumeTask::class.java)
            task.extension = ext

            then("it returns copilot --resume with session id") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args.shouldContainInOrder(listOf("--resume", "sess-456"))
            }
        }

        given("CopilotResumeTask.buildCommand with extraArgs") {
            val project = ProjectBuilder.builder().build()
            val ext = Copilot.SettingsExtension().apply { extraArgs = listOf("--verbose") }
            project.extensions.extraProperties["sessionId"] = "sess-456"
            val task = project.tasks.create("test-resume-extra", CopilotResumeTask::class.java)
            task.extension = ext

            then("it appends extra args") {
                val (_, args) = task.buildCommand()
                args.last() shouldBe "--verbose"
            }
        }

        given("CopilotResumeTask.buildCommand without sessionId") {
            val project = ProjectBuilder.builder().build()
            val ext = Copilot.SettingsExtension()
            val task = project.tasks.create("test-resume-fail", CopilotResumeTask::class.java)
            task.extension = ext

            then("it throws when sessionId is missing") {
                shouldThrow<IllegalStateException> {
                    task.buildCommand()
                }
            }
        }

        given("CopilotRunTask.buildCommand") {
            val project = ProjectBuilder.builder().build()
            val ext = Copilot.SettingsExtension()
            project.extensions.extraProperties["prompt"] = "hello"
            val task = project.tasks.create("test-run-cmd", CopilotRunTask::class.java)
            task.extension = ext

            then("it returns copilot with args") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "copilot"
                args.shouldContainInOrder(listOf("-m", "hello"))
            }
        }

        given("CopilotRunTask.buildCommand without prompt") {
            val project = ProjectBuilder.builder().build()
            val ext = Copilot.SettingsExtension()
            val task = project.tasks.create("test-run-cmd-fail", CopilotRunTask::class.java)
            task.extension = ext

            then("it throws when prompt is missing") {
                shouldThrow<IllegalStateException> {
                    task.buildCommand()
                }
            }
        }
    })
