package zone.clanker.agents.codex

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CodexExecTaskTest : BehaviorSpec({
    given("CodexExecTask.buildArgs") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        val task =
            project.tasks.create(
                "test-exec",
                CodexExecTask::class.java,
            )
        task.extension = ext

        `when`("called with defaults") {
            val args = task.buildArgs("Hello")
            then("first arg is the prompt") {
                args[0] shouldBe "Hello"
            }
            then("it does not include empty model") {
                args shouldNotContain "--model"
            }
            then("it does not include empty sandbox") {
                args shouldNotContain "--sandbox"
            }
            then("it does not include boolean flags") {
                args shouldNotContain "--full-auto"
                args shouldNotContain "--search"
                args shouldNotContain "--json"
                args shouldNotContain "--ephemeral"
                args shouldNotContain "--dangerously-bypass-approvals-and-sandbox"
            }
        }

        `when`("called with model") {
            ext.model = "codex-mini-latest"
            val args = task.buildArgs("Test")
            then("it includes model") {
                args.shouldContainInOrder(
                    listOf("--model", "codex-mini-latest"),
                )
            }
        }

        `when`("called with sandbox") {
            ext.sandbox = "docker"
            val args = task.buildArgs("Test")
            then("it includes sandbox") {
                args.shouldContainInOrder(
                    listOf("--sandbox", "docker"),
                )
            }
        }

        `when`("called with approval") {
            ext.approval = "full-auto"
            val args = task.buildArgs("Test")
            then("it includes approval") {
                args.shouldContainInOrder(
                    listOf("--approval", "full-auto"),
                )
            }
        }

        `when`("called with fullAuto") {
            ext.fullAuto = true
            val args = task.buildArgs("Test")
            then("it includes full-auto flag") {
                args shouldContain "--full-auto"
            }
        }

        `when`("called with search") {
            ext.search = true
            val args = task.buildArgs("Test")
            then("it includes search flag") {
                args shouldContain "--search"
            }
        }

        `when`("called with addDir") {
            ext.addDir = listOf("/tmp/dir1")
            val args = task.buildArgs("Test")
            then("it includes add-dir") {
                args shouldContain "--add-dir"
                args shouldContain "/tmp/dir1"
            }
        }

        `when`("called with outputSchema") {
            ext.outputSchema = "{\"type\":\"object\"}"
            val args = task.buildArgs("Test")
            then("it includes output schema") {
                args shouldContain "--output-schema"
            }
        }

        `when`("called with json") {
            ext.json = true
            val args = task.buildArgs("Test")
            then("it includes json flag") {
                args shouldContain "--json"
            }
        }

        `when`("called with ephemeral") {
            ext.ephemeral = true
            val args = task.buildArgs("Test")
            then("it includes ephemeral flag") {
                args shouldContain "--ephemeral"
            }
        }

        `when`("called with image") {
            ext.image = listOf("img.png")
            val args = task.buildArgs("Test")
            then("it includes image") {
                args shouldContain "--image"
                args shouldContain "img.png"
            }
        }

        `when`("called with dangerouslyBypass") {
            ext.dangerouslyBypass = true
            val args = task.buildArgs("Test")
            then("it includes dangerously-bypass flag") {
                args shouldContain "--dangerously-bypass-approvals-and-sandbox"
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
                Codex.SettingsExtension().apply {
                    model = "o4-mini"
                    sandbox = "docker"
                    approval = "auto-edit"
                    fullAuto = true
                    search = true
                    addDir = listOf("/tmp")
                    outputSchema = "{}"
                    json = true
                    ephemeral = true
                    image = listOf("a.png")
                    extraArgs = listOf("--extra")
                }
            val fullTask =
                project.tasks.create(
                    "test-full",
                    CodexExecTask::class.java,
                )
            fullTask.extension = fullExt
            val args = fullTask.buildArgs("Full")

            then("first arg is prompt") {
                args[0] shouldBe "Full"
            }
            then("model is included") {
                args.shouldContainInOrder(
                    listOf("--model", "o4-mini"),
                )
            }
            then("full-auto is included") {
                args shouldContain "--full-auto"
            }
        }
    }
})
