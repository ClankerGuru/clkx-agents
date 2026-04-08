package zone.clanker.agents.claude

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class ClaudeRunTaskTest : BehaviorSpec({
    given("ClaudeRunTask.buildArgs") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        val task = project.tasks.create("test-run", ClaudeRunTask::class.java)
        task.extension = ext

        `when`("called with defaults") {
            val args = task.buildArgs("Hello")
            then("it includes prompt flag") {
                args.shouldContainInOrder(listOf("-p", "Hello"))
            }
            then("it includes output format") {
                args.shouldContainInOrder(listOf("--output-format", "text"))
            }
            then("it does not include empty model") {
                args shouldNotContain "--model"
            }
            then("it does not include default permission mode") {
                args shouldNotContain "--permission-mode"
            }
            then("it does not include zero budget") {
                args shouldNotContain "--max-budget-usd"
            }
            then("it does not include empty effort") {
                args shouldNotContain "--effort"
            }
            then("it does not include empty system prompt") {
                args shouldNotContain "--system-prompt"
            }
            then("it does not include bypass flags") {
                args shouldNotContain "--bare"
                args shouldNotContain "--dangerously-skip-permissions"
                args shouldNotContain "--verbose"
                args shouldNotContain "--add-dir"
                args shouldNotContain "--append-system-prompt"
            }
        }

        `when`("called with model set") {
            ext.model = "opus"
            val args = task.buildArgs("Test")
            then("it includes model") {
                args.shouldContainInOrder(listOf("--model", "opus"))
            }
        }

        `when`("called with non-default permission mode") {
            ext.permissionMode = "bypassPermissions"
            val args = task.buildArgs("Test")
            then("it includes permission mode") {
                args.shouldContainInOrder(
                    listOf("--permission-mode", "bypassPermissions"),
                )
            }
        }

        `when`("called with effort") {
            ext.effort = "high"
            val args = task.buildArgs("Test")
            then("it includes effort") {
                args.shouldContainInOrder(listOf("--effort", "high"))
            }
        }

        `when`("called with budget") {
            ext.maxBudgetUsd = 5.0
            val args = task.buildArgs("Test")
            then("it includes budget") {
                args.shouldContainInOrder(listOf("--max-budget-usd", "5.0"))
            }
        }

        `when`("called with system prompt") {
            ext.systemPrompt = "Be helpful"
            val args = task.buildArgs("Test")
            then("it includes system prompt") {
                args.shouldContainInOrder(
                    listOf("--system-prompt", "Be helpful"),
                )
            }
        }

        `when`("called with allowed tools") {
            ext.allowedTools = listOf("Read", "Write")
            val args = task.buildArgs("Test")
            then("it includes allowed tools") {
                args shouldContain "--allowedTools"
                args shouldContain "Read"
                args shouldContain "Write"
            }
        }

        `when`("called with disallowed tools") {
            ext.disallowedTools = listOf("Bash")
            val args = task.buildArgs("Test")
            then("it includes disallowed tools") {
                args shouldContain "--disallowedTools"
                args shouldContain "Bash"
            }
        }

        `when`("called with bare") {
            ext.bare = true
            val args = task.buildArgs("Test")
            then("it includes bare flag") {
                args shouldContain "--bare"
            }
        }

        `when`("called with dangerouslySkipPermissions") {
            ext.dangerouslySkipPermissions = true
            val args = task.buildArgs("Test")
            then("it includes dangerously-skip-permissions flag") {
                args shouldContain "--dangerously-skip-permissions"
            }
        }

        `when`("called with verbose") {
            ext.verbose = true
            val args = task.buildArgs("Test")
            then("it includes verbose flag") {
                args shouldContain "--verbose"
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

        `when`("called with appendSystemPrompt") {
            ext.appendSystemPrompt = "Be concise"
            val args = task.buildArgs("Test")
            then("it includes append-system-prompt") {
                args.shouldContainInOrder(
                    listOf("--append-system-prompt", "Be concise"),
                )
            }
        }

        `when`("called with extra args") {
            ext.extraArgs = listOf("--verbose", "--debug")
            val args = task.buildArgs("Test")
            then("it appends extra args") {
                args shouldContain "--verbose"
                args shouldContain "--debug"
            }
        }

        `when`("called with all options set") {
            val fullExt =
                Claude.SettingsExtension().apply {
                    model = "sonnet"
                    outputFormat = "json"
                    permissionMode = "bypassPermissions"
                    maxBudgetUsd = 10.0
                    systemPrompt = "test prompt"
                    allowedTools = listOf("Read")
                    disallowedTools = listOf("Bash")
                    effort = "low"
                    extraArgs = listOf("--extra")
                }
            val fullTask =
                project.tasks.create(
                    "test-full",
                    ClaudeRunTask::class.java,
                )
            fullTask.extension = fullExt
            val args = fullTask.buildArgs("Full test")

            then("first args are prompt") {
                args[0] shouldBe "-p"
                args[1] shouldBe "Full test"
            }
            then("model is included") {
                args.shouldContainInOrder(listOf("--model", "sonnet"))
            }
            then("output format is json") {
                args.shouldContainInOrder(listOf("--output-format", "json"))
            }
            then("permission mode is set") {
                args.shouldContainInOrder(
                    listOf("--permission-mode", "bypassPermissions"),
                )
            }
            then("effort is set") {
                args.shouldContainInOrder(listOf("--effort", "low"))
            }
            then("budget is set") {
                args.shouldContainInOrder(
                    listOf("--max-budget-usd", "10.0"),
                )
            }
            then("system prompt is set") {
                args.shouldContainInOrder(
                    listOf("--system-prompt", "test prompt"),
                )
            }
        }
    }
})
