package zone.clanker.agents.claude

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class ClaudeTaskCommandsTest : BehaviorSpec({
    given("ClaudeAuthTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-auth", ClaudeAuthTask::class.java)

        then("it returns claude auth status") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("auth", "status")
        }
    }

    given("ClaudeVersionTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-version", ClaudeVersionTask::class.java)

        then("it returns claude --version") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("--version")
        }
    }

    given("ClaudeDoctorTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-doctor", ClaudeDoctorTask::class.java)

        then("it returns claude doctor") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("doctor")
        }
    }

    given("ClaudeAgentsTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-agents", ClaudeAgentsTask::class.java)

        then("it returns claude agents") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("agents")
        }
    }

    given("ClaudeMcpListTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-mcp-list", ClaudeMcpListTask::class.java)

        then("it returns claude mcp list") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("mcp", "list")
        }
    }

    given("ClaudeMcpServeTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-mcp-serve", ClaudeMcpServeTask::class.java)

        then("it returns claude mcp serve") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("mcp", "serve")
        }
    }

    given("ClaudeUpdateTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-update", ClaudeUpdateTask::class.java)

        then("it returns claude update") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("update")
        }
    }

    given("ClaudeSetupTokenTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-setup-token", ClaudeSetupTokenTask::class.java)

        then("it returns claude setup-token") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("setup-token")
        }
    }

    given("ClaudeMcpAddTask.buildCommand") {
        val project = ProjectBuilder.builder().withName("my-server").build()
        project.extensions.extraProperties["transport"] = "stdio"
        val task = project.tasks.create("test-mcp-add", ClaudeMcpAddTask::class.java)

        `when`("name and transport are set") {
            then("it returns claude mcp add with name and transport") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "claude"
                args shouldBe listOf("mcp", "add", "my-server", "stdio")
            }
        }
    }

    given("ClaudeMcpAddTask.buildCommand without transport") {
        val project = ProjectBuilder.builder().withName("my-server").build()
        val task = project.tasks.create("test-mcp-add-no-transport", ClaudeMcpAddTask::class.java)

        then("it throws when transport is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }

    given("ClaudeMcpRemoveTask.buildCommand") {
        val project = ProjectBuilder.builder().withName("my-server").build()
        val task = project.tasks.create("test-mcp-remove", ClaudeMcpRemoveTask::class.java)

        then("it returns claude mcp remove with name") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("mcp", "remove", "my-server")
        }
    }

    given("ClaudeInstallTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val task = project.tasks.create("test-install", ClaudeInstallTask::class.java)

        `when`("no target is set") {
            then("it returns claude install") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "claude"
                args shouldBe listOf("install")
            }
        }
    }

    given("ClaudeInstallTask.buildCommand with target") {
        val project = ProjectBuilder.builder().build()
        project.extensions.extraProperties["target"] = "/usr/local"
        val task = project.tasks.create("test-install-target", ClaudeInstallTask::class.java)

        then("it includes the target") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args shouldBe listOf("install", "/usr/local")
        }
    }

    given("ClaudeResumeTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        project.extensions.extraProperties["sessionId"] = "abc-123"
        val task = project.tasks.create("test-resume", ClaudeResumeTask::class.java)
        task.extension = ext

        `when`("only sessionId is set") {
            then("it returns resume with session id") {
                val (binary, args) = task.buildCommand()
                binary shouldBe "claude"
                args.shouldContainInOrder(listOf("-r", "abc-123"))
            }
        }
    }

    given("ClaudeResumeTask.buildCommand with prompt") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        project.extensions.extraProperties["sessionId"] = "abc-123"
        project.extensions.extraProperties["prompt"] = "continue"
        val task = project.tasks.create("test-resume-prompt", ClaudeResumeTask::class.java)
        task.extension = ext

        then("it includes the prompt") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args.shouldContainInOrder(listOf("-r", "abc-123"))
            args.shouldContainInOrder(listOf("-p", "continue"))
        }
    }

    given("ClaudeResumeTask.buildCommand with fork") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        project.extensions.extraProperties["sessionId"] = "abc-123"
        project.extensions.extraProperties["forkSession"] = "true"
        val task = project.tasks.create("test-resume-fork", ClaudeResumeTask::class.java)
        task.extension = ext

        then("it includes --fork") {
            val (_, args) = task.buildCommand()
            args.last() shouldBe "--fork"
        }
    }

    given("ClaudeResumeTask.buildCommand with extraArgs") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension().apply { extraArgs = listOf("--verbose") }
        project.extensions.extraProperties["sessionId"] = "abc-123"
        val task = project.tasks.create("test-resume-extra", ClaudeResumeTask::class.java)
        task.extension = ext

        then("it appends extra args") {
            val (_, args) = task.buildCommand()
            args.last() shouldBe "--verbose"
        }
    }

    given("ClaudeResumeTask.buildCommand without sessionId") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        val task = project.tasks.create("test-resume-fail", ClaudeResumeTask::class.java)
        task.extension = ext

        then("it throws when sessionId is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }

    given("ClaudeRunTask.buildCommand") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        project.extensions.extraProperties["prompt"] = "hello"
        val task = project.tasks.create("test-run-cmd", ClaudeRunTask::class.java)
        task.extension = ext

        then("it returns claude with args") {
            val (binary, args) = task.buildCommand()
            binary shouldBe "claude"
            args.shouldContainInOrder(listOf("-p", "hello"))
        }
    }

    given("ClaudeRunTask.buildCommand without prompt") {
        val project = ProjectBuilder.builder().build()
        val ext = Claude.SettingsExtension()
        val task = project.tasks.create("test-run-cmd-fail", ClaudeRunTask::class.java)
        task.extension = ext

        then("it throws when prompt is missing") {
            shouldThrow<IllegalStateException> {
                task.buildCommand()
            }
        }
    }
})
