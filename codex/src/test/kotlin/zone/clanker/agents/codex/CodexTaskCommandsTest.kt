package zone.clanker.agents.codex

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CodexTaskCommandsTest : BehaviorSpec({
    given("CodexAuthTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-auth", CodexAuthTask::class.java)

        then("it returns codex login status") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("login", "status")
        }
    }

    given("CodexVersionTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-version", CodexVersionTask::class.java)

        then("it returns codex --version") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("--version")
        }
    }

    given("CodexApplyTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-apply", CodexApplyTask::class.java)

        then("it returns codex apply") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("apply")
        }
    }

    given("CodexMcpListTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-mcp-list", CodexMcpListTask::class.java)

        then("it returns codex mcp list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("mcp", "list")
        }
    }

    given("CodexFeaturesTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-features", CodexFeaturesTask::class.java)

        then("it returns codex features list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("features", "list")
        }
    }

    given("CodexCloudListTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-cloud-list", CodexCloudListTask::class.java)

        then("it returns codex cloud list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("cloud", "list")
        }
    }

    given("CodexMcpAddTask.buildCommand") {
        val project = ProjectBuilder.builder().withName("my-mcp").build()
        val task = project.tasks.create("test-mcp-add", CodexMcpAddTask::class.java)

        then("it returns codex mcp add with name") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("mcp", "add", "my-mcp")
        }
    }

    given("CodexMcpRemoveTask.buildCommand") {
        val project = ProjectBuilder.builder().withName("my-mcp").build()
        val task = project.tasks.create("test-mcp-remove", CodexMcpRemoveTask::class.java)

        then("it returns codex mcp remove with name") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("mcp", "remove", "my-mcp")
        }
    }

    given("CodexCompletionTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["shell"] = "zsh"
        val task = project.tasks.create("test-completion", CodexCompletionTask::class.java)

        then("it returns codex completion with shell") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("completion", "zsh")
        }
    }

    given("CodexCompletionTask.buildCommand without shell") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-completion-fail", CodexCompletionTask::class.java)

        then("it throws when shell is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }

    given("CodexResumeTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-resume", CodexResumeTask::class.java)

        `when`("no last flag") {
            then("it returns codex resume") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "codex"
                args shouldBe listOf("resume")
            }
        }
    }

    given("CodexResumeTask.buildCommand with last") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["last"] = "true"
        val task = project.tasks.create("test-resume-last", CodexResumeTask::class.java)

        then("it includes --last") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("resume", "--last")
        }
    }

    given("CodexForkTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-fork", CodexForkTask::class.java)

        `when`("no last flag") {
            then("it returns codex fork") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "codex"
                args shouldBe listOf("fork")
            }
        }
    }

    given("CodexForkTask.buildCommand with last") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["last"] = "true"
        val task = project.tasks.create("test-fork-last", CodexForkTask::class.java)

        then("it includes --last") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args shouldBe listOf("fork", "--last")
        }
    }

    given("CodexReviewTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        val task = project.tasks.create("test-review", CodexReviewTask::class.java)
        task.extension = ext

        `when`("called with defaults") {
            then("it returns codex review") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "codex"
                args shouldBe listOf("review")
            }
        }
    }

    given("CodexReviewTask.buildCommand with base") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        project.extensions.extraProperties["base"] = "main"
        val task = project.tasks.create("test-review-base", CodexReviewTask::class.java)
        task.extension = ext

        then("it includes --base") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--base", "main"))
        }
    }

    given("CodexReviewTask.buildCommand with commit") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        project.extensions.extraProperties["commit"] = "abc123"
        val task = project.tasks.create("test-review-commit", CodexReviewTask::class.java)
        task.extension = ext

        then("it includes --commit") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--commit", "abc123"))
        }
    }

    given("CodexReviewTask.buildCommand with uncommitted") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        project.extensions.extraProperties["uncommitted"] = "true"
        val task = project.tasks.create("test-review-uncommitted", CodexReviewTask::class.java)
        task.extension = ext

        then("it includes --uncommitted") {
            val (_, args) = task.buildCommand()
            args shouldContain "--uncommitted"
        }
    }

    given("CodexReviewTask.buildCommand with title") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        project.extensions.extraProperties["title"] = "My PR"
        val task = project.tasks.create("test-review-title", CodexReviewTask::class.java)
        task.extension = ext

        then("it includes --title") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--title", "My PR"))
        }
    }

    given("CodexReviewTask.buildCommand with extraArgs") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension().apply { extraArgs = listOf("--verbose") }
        val task = project.tasks.create("test-review-extra", CodexReviewTask::class.java)
        task.extension = ext

        then("it appends extra args") {
            val (_, args) = task.buildCommand()
            args shouldContain "--verbose"
        }
    }

    given("CodexReviewTask.buildCommand with all options") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension().apply { extraArgs = listOf("--extra") }
        project.extensions.extraProperties["base"] = "main"
        project.extensions.extraProperties["commit"] = "abc123"
        project.extensions.extraProperties["uncommitted"] = "true"
        project.extensions.extraProperties["title"] = "Review"
        val task = project.tasks.create("test-review-all", CodexReviewTask::class.java)
        task.extension = ext

        then("it includes all flags") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args[0] shouldBe "review"
            args shouldContain "--base"
            args shouldContain "--commit"
            args shouldContain "--uncommitted"
            args shouldContain "--title"
            args shouldContain "--extra"
        }
    }

    given("CodexExecTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        project.extensions.extraProperties["prompt"] = "fix bugs"
        val task = project.tasks.create("test-exec-cmd", CodexExecTask::class.java)
        task.extension = ext

        then("it returns codex with args") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "codex"
            args[0] shouldBe "fix bugs"
        }
    }

    given("CodexExecTask.buildCommand without prompt") {
        val project = ProjectBuilder.builder().build()
        val ext = Codex.SettingsExtension()
        val task = project.tasks.create("test-exec-cmd-fail", CodexExecTask::class.java)
        task.extension = ext

        then("it throws when prompt is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }
})
