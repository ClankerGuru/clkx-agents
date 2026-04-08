package zone.clanker.agents.opencode

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class OpenCodePluginTest : BehaviorSpec({
    given("OpenCodePlugin") {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("zone.clanker.opencode")

        `when`("applied") {
            then("it registers all tasks") {
                project.tasks.findByName("opencode-run").shouldNotBeNull()
                project.tasks.findByName("opencode-auth").shouldNotBeNull()
                project.tasks.findByName("opencode-version").shouldNotBeNull()
                project.tasks.findByName("opencode-stats").shouldNotBeNull()
            }

            then("it registers the extension") {
                project.extensions.findByType(OpenCodeExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("OpenCodeExtension") {
        val ext = OpenCodeExtension()

        `when`("created with defaults") {
            then("it has correct defaults") {
                ext.model shouldBe ""
                ext.format shouldBe ""
                ext.agent shouldBe ""
                ext.variant shouldBe ""
                ext.thinking shouldBe false
                ext.file shouldBe emptyList()
                ext.dir shouldBe ""
                ext.share shouldBe false
                ext.extraArgs shouldBe emptyList()
            }
        }
    }
})
