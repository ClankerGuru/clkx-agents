plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")
    implementation("com.vanniktech:gradle-maven-publish-plugin:0.32.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.2.0")
}
