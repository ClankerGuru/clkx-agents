package zone.clanker.agents.opencode

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class OpenCodeTaskCommandsTest : BehaviorSpec({
    given("OpenCodeAuthTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-auth", OpenCodeAuthTask::class.java)

        then("it returns opencode providers list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("providers", "list")
        }
    }

    given("OpenCodeVersionTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-version", OpenCodeVersionTask::class.java)

        then("it returns opencode --version") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("--version")
        }
    }

    given("OpenCodeMcpListTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-mcp-list", OpenCodeMcpListTask::class.java)

        then("it returns opencode mcp list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("mcp", "list")
        }
    }

    given("OpenCodeMcpAddTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-mcp-add", OpenCodeMcpAddTask::class.java)

        then("it returns opencode mcp add") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("mcp", "add")
        }
    }

    given("OpenCodeSessionListTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-session-list", OpenCodeSessionListTask::class.java)

        then("it returns opencode session list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("session", "list")
        }
    }

    given("OpenCodeAgentListTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-agent-list", OpenCodeAgentListTask::class.java)

        then("it returns opencode agent list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("agent", "list")
        }
    }

    given("OpenCodeAgentCreateTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-agent-create", OpenCodeAgentCreateTask::class.java)

        then("it returns opencode agent create") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("agent", "create")
        }
    }

    given("OpenCodeServeTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-serve", OpenCodeServeTask::class.java)

        then("it returns opencode serve") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("serve")
        }
    }

    given("OpenCodeGithubInstallTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-gh-install", OpenCodeGithubInstallTask::class.java)

        then("it returns opencode github install") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("github", "install")
        }
    }

    given("OpenCodeGithubRunTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-gh-run", OpenCodeGithubRunTask::class.java)

        then("it returns opencode github run") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("github", "run")
        }
    }

    given("OpenCodeSessionDeleteTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["sessionId"] = "sess-789"
        val task = project.tasks.create("test-session-delete", OpenCodeSessionDeleteTask::class.java)

        then("it returns opencode session delete with id") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("session", "delete", "sess-789")
        }
    }

    given("OpenCodeSessionDeleteTask.buildCommand without sessionId") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-session-delete-fail", OpenCodeSessionDeleteTask::class.java)

        then("it throws when sessionId is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }

    given("OpenCodeImportTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["file"] = "export.json"
        val task = project.tasks.create("test-import", OpenCodeImportTask::class.java)

        then("it returns opencode import with file") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("import", "export.json")
        }
    }

    given("OpenCodeImportTask.buildCommand without file") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-import-fail", OpenCodeImportTask::class.java)

        then("it throws when file is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }

    given("OpenCodePrTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["number"] = "42"
        val task = project.tasks.create("test-pr", OpenCodePrTask::class.java)

        then("it returns opencode pr with number") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("pr", "42")
        }
    }

    given("OpenCodePrTask.buildCommand without number") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-pr-fail", OpenCodePrTask::class.java)

        then("it throws when number is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }

    given("OpenCodeStatsTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-stats", OpenCodeStatsTask::class.java)

        `when`("called with defaults") {
            then("it returns opencode stats") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "opencode"
                args shouldBe listOf("stats")
            }
        }
    }

    given("OpenCodeStatsTask.buildCommand with days") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["days"] = "7"
        val task = project.tasks.create("test-stats-days", OpenCodeStatsTask::class.java)

        then("it includes --days") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--days", "7"))
        }
    }

    given("OpenCodeStatsTask.buildCommand with tools") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["tools"] = "true"
        val task = project.tasks.create("test-stats-tools", OpenCodeStatsTask::class.java)

        then("it includes --tools") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--tools", "true"))
        }
    }

    given("OpenCodeStatsTask.buildCommand with models") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["models"] = "true"
        val task = project.tasks.create("test-stats-models", OpenCodeStatsTask::class.java)

        then("it includes --models") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--models", "true"))
        }
    }

    given("OpenCodeStatsTask.buildCommand with statsProject") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["statsProject"] = "my-proj"
        val task = project.tasks.create("test-stats-proj", OpenCodeStatsTask::class.java)

        then("it includes --project") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--project", "my-proj"))
        }
    }

    given("OpenCodeStatsTask.buildCommand with all options") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["days"] = "30"
        project.extensions.extraProperties["tools"] = "yes"
        project.extensions.extraProperties["models"] = "yes"
        project.extensions.extraProperties["statsProject"] = "proj"
        val task = project.tasks.create("test-stats-all", OpenCodeStatsTask::class.java)

        then("it includes all flags") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args[0] shouldBe "stats"
            args shouldContain "--days"
            args shouldContain "--tools"
            args shouldContain "--models"
            args shouldContain "--project"
        }
    }

    given("OpenCodeModelsTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-models", OpenCodeModelsTask::class.java)

        `when`("called with defaults") {
            then("it returns opencode models") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "opencode"
                args shouldBe listOf("models")
            }
        }
    }

    given("OpenCodeModelsTask.buildCommand with provider") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["provider"] = "anthropic"
        val task = project.tasks.create("test-models-provider", OpenCodeModelsTask::class.java)

        then("it includes --provider") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--provider", "anthropic"))
        }
    }

    given("OpenCodeExportTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-export", OpenCodeExportTask::class.java)

        `when`("called with defaults") {
            then("it returns opencode export") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "opencode"
                args shouldBe listOf("export")
            }
        }
    }

    given("OpenCodeExportTask.buildCommand with sessionId") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["sessionId"] = "sess-export"
        val task = project.tasks.create("test-export-session", OpenCodeExportTask::class.java)

        then("it includes --session") {
            val (_, args) = task.buildCommand()
            args.shouldContainInOrder(listOf("--session", "sess-export"))
        }
    }

    given("OpenCodeUpgradeTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-upgrade", OpenCodeUpgradeTask::class.java)

        `when`("no target") {
            then("it returns opencode upgrade") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "opencode"
                args shouldBe listOf("upgrade")
            }
        }
    }

    given("OpenCodeUpgradeTask.buildCommand with target") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["target"] = "v2.0.0"
        val task = project.tasks.create("test-upgrade-target", OpenCodeUpgradeTask::class.java)

        then("it includes the target") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args shouldBe listOf("upgrade", "v2.0.0")
        }
    }

    given("OpenCodeRunTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val ext = OpenCode.SettingsExtension()
        project.extensions.extraProperties["prompt"] = "hello"
        val task = project.tasks.create("test-run-cmd", OpenCodeRunTask::class.java)
        task.extension = ext

        then("it returns opencode with args") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "opencode"
            args.shouldContainInOrder(listOf("--prompt", "hello"))
        }
    }

    given("OpenCodeRunTask.buildCommand without prompt") {
        val project = ProjectBuilder.builder().build()
        val ext = OpenCode.SettingsExtension()
        val task = project.tasks.create("test-run-cmd-fail", OpenCodeRunTask::class.java)
        task.extension = ext

        then("it throws when prompt is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }
})
