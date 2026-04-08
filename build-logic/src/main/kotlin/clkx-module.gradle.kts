plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jetbrains.kotlinx.kover")
}

kotlin { jvmToolchain(17) }

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kover {
    reports {
        verify {
            rule {
                minBound(45)
            }
        }
    }
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
}
