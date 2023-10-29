import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.8.0"
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.0.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("Project1.Project1")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/dev.robocode.tankroyale/robocode-tankroyale-bot-api
    implementation("dev.robocode.tankroyale:robocode-tankroyale-bot-api:0.19.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    archiveFileName.set("Project1.jar")

    doLast {
        copy {
            from("src/main/kotlin/Project1/Project1.json")
            from("$buildDir/libs/Project1.jar")
            into("Project1")
        }
    }

}

tasks.clean {
    delete ("Project1/Project1.json")
    delete ("Project1/Project1.jar")
}

kotlin {
    jvmToolchain(11)
}
