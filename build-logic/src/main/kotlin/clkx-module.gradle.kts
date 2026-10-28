plugins {
    `java-library`
    kotlin("jvm")
}

group = project.property("GROUP") as String

dependencies {
    "implementation"(gradleApi())
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}
