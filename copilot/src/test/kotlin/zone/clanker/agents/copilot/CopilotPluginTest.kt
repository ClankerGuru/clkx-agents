package zone.clanker.agents.copilot

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CopilotPluginTest : BehaviorSpec({
    given("CopilotPlugin") {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("zone.clanker.copilot")

        `when`("applied") {
            then("it registers all tasks") {
                project.tasks.findByName("copilot-run").shouldNotBeNull()
                project.tasks.findByName("copilot-resume").shouldNotBeNull()
                project.tasks.findByName("copilot-init").shouldNotBeNull()
                project.tasks.findByName("copilot-auth").shouldNotBeNull()
                project.tasks.findByName("copilot-version").shouldNotBeNull()
            }

            then("it registers the extension") {
                project.extensions.findByType(CopilotExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("CopilotExtension") {
        val ext = CopilotExtension()

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
                ext.extraArgs shouldBe emptyList()
            }
        }
    }
})
