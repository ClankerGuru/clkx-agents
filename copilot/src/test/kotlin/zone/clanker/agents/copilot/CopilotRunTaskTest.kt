package zone.clanker.agents.copilot

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CopilotRunTaskTest : BehaviorSpec({
    given("CopilotRunTask.buildArgs") {
        val project = ProjectBuilder.builder().build()
        val ext = Copilot.SettingsExtension()
        val task =
            project.tasks.create(
                "test-run",
                CopilotRunTask::class.java,
            )
        task.extension = ext

        `when`("called with defaults") {
            val args = task.buildArgs("Hello")
            then("it includes prompt flag") {
                args.shouldContainInOrder(listOf("-m", "Hello"))
            }
            then("it does not include empty model") {
                args shouldNotContain "--model"
            }
            then("it does not include empty effort") {
                args shouldNotContain "--effort"
            }
            then("it does not include boolean flags") {
                args shouldNotContain "--allow-all"
                args shouldNotContain "--allow-all-tools"
                args shouldNotContain "--allow-all-paths"
                args shouldNotContain "--autopilot"
                args shouldNotContain "--silent"
                args shouldNotContain "--yolo"
                args shouldNotContain "--allow-all-urls"
                args shouldNotContain "--no-ask-user"
                args shouldNotContain "--no-custom-instructions"
            }
        }

        `when`("called with model") {
            ext.model = "gpt-4o"
            val args = task.buildArgs("Test")
            then("it includes model") {
                args.shouldContainInOrder(listOf("--model", "gpt-4o"))
            }
        }

        `when`("called with effort") {
            ext.effort = "high"
            val args = task.buildArgs("Test")
            then("it includes effort") {
                args.shouldContainInOrder(listOf("--effort", "high"))
            }
        }

        `when`("called with outputFormat") {
            ext.outputFormat = "json"
            val args = task.buildArgs("Test")
            then("it includes output format") {
                args.shouldContainInOrder(
                    listOf("--output-format", "json"),
                )
            }
        }

        `when`("called with boolean flags enabled") {
            ext.allowAll = true
            ext.allowAllTools = true
            ext.allowAllPaths = true
            ext.autopilot = true
            ext.silent = true
            val args = task.buildArgs("Test")
            then("it includes all boolean flags") {
                args shouldContain "--allow-all"
                args shouldContain "--allow-all-tools"
                args shouldContain "--allow-all-paths"
                args shouldContain "--autopilot"
                args shouldContain "--silent"
            }
        }

        `when`("called with agent") {
            ext.agent = "my-agent"
            val args = task.buildArgs("Test")
            then("it includes agent") {
                args.shouldContainInOrder(
                    listOf("--agent", "my-agent"),
                )
            }
        }

        `when`("called with addDir") {
            ext.addDir = listOf("/tmp/dir1", "/tmp/dir2")
            val args = task.buildArgs("Test")
            then("it includes add-dir flags") {
                args shouldContain "--add-dir"
                args shouldContain "/tmp/dir1"
                args shouldContain "/tmp/dir2"
            }
        }

        `when`("called with configDir") {
            ext.configDir = "/tmp/config"
            val args = task.buildArgs("Test")
            then("it includes config dir") {
                args.shouldContainInOrder(
                    listOf("--config-dir", "/tmp/config"),
                )
            }
        }

        `when`("called with yolo") {
            ext.yolo = true
            val args = task.buildArgs("Test")
            then("it includes yolo flag") {
                args shouldContain "--yolo"
            }
        }

        `when`("called with yolo overrides allowAll") {
            val yoloExt =
                Copilot.SettingsExtension().apply {
                    yolo = true
                    allowAll = true
                }
            val yoloTask =
                project.tasks.create(
                    "test-yolo",
                    CopilotRunTask::class.java,
                )
            yoloTask.extension = yoloExt
            val args = yoloTask.buildArgs("Test")
            then("it includes yolo but not allow-all") {
                args shouldContain "--yolo"
                args shouldNotContain "--allow-all"
            }
        }

        `when`("called with allowAllUrls") {
            ext.allowAllUrls = true
            val args = task.buildArgs("Test")
            then("it includes allow-all-urls flag") {
                args shouldContain "--allow-all-urls"
            }
        }

        `when`("called with noAskUser") {
            ext.noAskUser = true
            val args = task.buildArgs("Test")
            then("it includes no-ask-user flag") {
                args shouldContain "--no-ask-user"
            }
        }

        `when`("called with noCustomInstructions") {
            ext.noCustomInstructions = true
            val args = task.buildArgs("Test")
            then("it includes no-custom-instructions flag") {
                args shouldContain "--no-custom-instructions"
            }
        }

        `when`("called with extra args") {
            ext.extraArgs = listOf("--verbose")
            val args = task.buildArgs("Test")
            then("it appends extra args") {
                args shouldContain "--verbose"
            }
        }

        `when`("called with all options") {
            val fullExt =
                Copilot.SettingsExtension().apply {
                    model = "gpt-4o"
                    effort = "high"
                    outputFormat = "json"
                    allowAll = true
                    allowAllTools = true
                    allowAllPaths = true
                    autopilot = true
                    silent = true
                    agent = "test-agent"
                    addDir = listOf("/tmp")
                    configDir = "/config"
                    extraArgs = listOf("--extra")
                }
            val fullTask =
                project.tasks.create(
                    "test-full",
                    CopilotRunTask::class.java,
                )
            fullTask.extension = fullExt
            val args = fullTask.buildArgs("Full")

            then("first args are prompt") {
                args[0] shouldBe "-m"
                args[1] shouldBe "Full"
            }
            then("model is included") {
                args.shouldContainInOrder(
                    listOf("--model", "gpt-4o"),
                )
            }
        }
    }
})
