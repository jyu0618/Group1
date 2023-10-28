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
    mainClass.set("org.example.Grp1")
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
    archiveFileName.set("Grp1.jar")

    doLast {
        copy {
            from("src/main/kotlin/org/example/Grp1.json")
            from("$buildDir/libs/Grp1.jar")
            into("Grp1")
        }
    }

}

tasks.clean {
    delete ("Grp1/Grp1.json")
    delete ("Grp1/Grp1.jar")
}

kotlin {
    jvmToolchain(11)
}
