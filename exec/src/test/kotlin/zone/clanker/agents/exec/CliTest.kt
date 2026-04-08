package zone.clanker.agents.exec

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class CliTest : BehaviorSpec({
    given("Cli.exec") {
        `when`("running echo") {
            val result = Cli.exec("echo", listOf("hello"))
            then("it captures stdout") {
                result.stdout.trim() shouldBe "hello"
                result.success shouldBe true
            }
        }

        `when`("running a nonexistent command") {
            val result = Cli.exec("nonexistent_binary_xyz")
            then("it fails") {
                result.success shouldBe false
            }
        }

        `when`("running with stdin") {
            val result = Cli.exec(CliRequest(binary = "cat", stdin = "piped input"))
            then("it reads stdin") {
                result.stdout shouldContain "piped input"
            }
        }

        `when`("running with timeout") {
            val result = Cli.exec(CliRequest(binary = "sleep", args = listOf("0.1"), timeoutSeconds = 5))
            then("it completes within timeout") {
                result.success shouldBe true
            }
        }

        `when`("running with env") {
            val result = Cli.exec(CliRequest(binary = "env", env = mapOf("TEST_VAR" to "test_value")))
            then("it passes env vars") {
                result.stdout shouldContain "TEST_VAR=test_value"
            }
        }
    }

    given("Cli.which") {
        `when`("checking for echo") {
            then("it finds echo") {
                Cli.which("echo") shouldBe true
            }
        }

        `when`("checking for nonexistent binary") {
            then("it returns false") {
                Cli.which("nonexistent_binary_xyz") shouldBe false
            }
        }
    }
})
