package zone.clanker.agents.claude

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class ClaudePluginTest : BehaviorSpec({
    given("ClaudePlugin") {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("zone.clanker.claude")

        `when`("applied") {
            then("it registers all tasks") {
                project.tasks.findByName("claude-run").shouldNotBeNull()
                project.tasks.findByName("claude-resume").shouldNotBeNull()
                project.tasks.findByName("claude-auth").shouldNotBeNull()
                project.tasks.findByName("claude-version").shouldNotBeNull()
                project.tasks.findByName("claude-doctor").shouldNotBeNull()
            }

            then("it registers the extension") {
                project.extensions.findByType(ClaudeExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("ClaudeExtension") {
        val ext = ClaudeExtension()

        `when`("created with defaults") {
            then("it has correct defaults") {
                ext.model shouldBe ""
                ext.outputFormat shouldBe "text"
                ext.permissionMode shouldBe "default"
                ext.maxBudgetUsd shouldBe 0.0
                ext.systemPrompt shouldBe ""
                ext.allowedTools shouldBe emptyList()
                ext.disallowedTools shouldBe emptyList()
                ext.effort shouldBe ""
                ext.extraArgs shouldBe emptyList()
            }
        }
    }
})
