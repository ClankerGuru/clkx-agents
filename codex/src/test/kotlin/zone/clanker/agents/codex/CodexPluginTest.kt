package zone.clanker.agents.codex

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.gradle.testfixtures.ProjectBuilder

class CodexPluginTest : BehaviorSpec({
    given("CodexPlugin") {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("zone.clanker.codex")

        `when`("applied") {
            then("it registers all tasks") {
                project.tasks.findByName("codex-exec").shouldNotBeNull()
                project.tasks.findByName("codex-review").shouldNotBeNull()
                project.tasks.findByName("codex-auth").shouldNotBeNull()
                project.tasks.findByName("codex-version").shouldNotBeNull()
            }

            then("it registers the extension") {
                project.extensions.findByType(CodexExtension::class.java).shouldNotBeNull()
            }
        }
    }

    given("CodexExtension") {
        val ext = CodexExtension()

        `when`("created with defaults") {
            then("it has correct defaults") {
                ext.model shouldBe ""
                ext.sandbox shouldBe ""
                ext.approval shouldBe ""
                ext.fullAuto shouldBe false
                ext.search shouldBe false
                ext.addDir shouldBe emptyList()
                ext.outputSchema shouldBe ""
                ext.json shouldBe false
                ext.ephemeral shouldBe false
                ext.image shouldBe emptyList()
                ext.extraArgs shouldBe emptyList()
            }
        }
    }
})
