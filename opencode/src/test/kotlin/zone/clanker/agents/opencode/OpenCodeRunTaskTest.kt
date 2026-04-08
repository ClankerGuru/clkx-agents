package zone.clanker.agents.opencode

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class OpenCodeRunTaskTest :
    BehaviorSpec({
        given("OpenCodeRunTask.buildArgs") {
            val project = ProjectBuilder.builder().build()
            val ext = OpenCode.SettingsExtension()
            val task =
                project.tasks.create(
                    "test-run",
                    OpenCodeRunTask::class.java,
                )
            task.extension = ext

            `when`("called with defaults") {
                val args = task.buildArgs("Hello")
                then("it includes prompt flag") {
                    args.shouldContainInOrder(
                        listOf("--prompt", "Hello"),
                    )
                }
                then("it does not include empty model") {
                    args shouldNotContain "--model"
                }
                then("it does not include empty format") {
                    args shouldNotContain "--format"
                }
                then("it does not include boolean flags") {
                    args shouldNotContain "--thinking"
                    args shouldNotContain "--share"
                    args shouldNotContain "--pure"
                    args shouldNotContain "--title"
                    args shouldNotContain "--continue"
                }
            }

            `when`("called with model") {
                ext.model = "anthropic/claude-sonnet-4-20250514"
                val args = task.buildArgs("Test")
                then("it includes model") {
                    args shouldContain "--model"
                    args shouldContain "anthropic/claude-sonnet-4-20250514"
                }
            }

            `when`("called with format") {
                ext.format = "json"
                val args = task.buildArgs("Test")
                then("it includes format") {
                    args.shouldContainInOrder(
                        listOf("--format", "json"),
                    )
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

            `when`("called with variant") {
                ext.variant = "fast"
                val args = task.buildArgs("Test")
                then("it includes variant") {
                    args.shouldContainInOrder(
                        listOf("--variant", "fast"),
                    )
                }
            }

            `when`("called with thinking") {
                ext.thinking = true
                val args = task.buildArgs("Test")
                then("it includes thinking flag") {
                    args shouldContain "--thinking"
                }
            }

            `when`("called with file") {
                ext.file = listOf("a.kt", "b.kt")
                val args = task.buildArgs("Test")
                then("it includes file flags") {
                    args shouldContain "--file"
                    args shouldContain "a.kt"
                    args shouldContain "b.kt"
                }
            }

            `when`("called with dir") {
                ext.dir = "/tmp/project"
                val args = task.buildArgs("Test")
                then("it includes dir") {
                    args.shouldContainInOrder(
                        listOf("--dir", "/tmp/project"),
                    )
                }
            }

            `when`("called with share") {
                ext.share = true
                val args = task.buildArgs("Test")
                then("it includes share flag") {
                    args shouldContain "--share"
                }
            }

            `when`("called with pure") {
                ext.pure = true
                val args = task.buildArgs("Test")
                then("it includes pure flag") {
                    args shouldContain "--pure"
                }
            }

            `when`("called with title") {
                ext.title = "My Session"
                val args = task.buildArgs("Test")
                then("it includes title") {
                    args.shouldContainInOrder(
                        listOf("--title", "My Session"),
                    )
                }
            }

            `when`("called with continueSession") {
                ext.continueSession = true
                val args = task.buildArgs("Test")
                then("it includes continue flag") {
                    args shouldContain "--continue"
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
                    OpenCode.SettingsExtension().apply {
                        model = "anthropic/claude-sonnet-4-20250514"
                        format = "json"
                        agent = "test-agent"
                        variant = "fast"
                        thinking = true
                        file = listOf("f.kt")
                        dir = "/tmp"
                        share = true
                        extraArgs = listOf("--extra")
                    }
                val fullTask =
                    project.tasks.create(
                        "test-full",
                        OpenCodeRunTask::class.java,
                    )
                fullTask.extension = fullExt
                val args = fullTask.buildArgs("Full")

                then("first args are prompt") {
                    args[0] shouldBe "--prompt"
                    args[1] shouldBe "Full"
                }
                then("thinking is included") {
                    args shouldContain "--thinking"
                }
                then("share is included") {
                    args shouldContain "--share"
                }
            }
        }
    })
