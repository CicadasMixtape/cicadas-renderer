@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.4.20"
    kotlin("plugin.serialization") version "2.4.20"

    id("fabric-loom") version "1.16-SNAPSHOT"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_name") as String)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

repositories {
}

sourceSets {
    create("testmod") {
        compileClasspath += main.get().compileClasspath
        runtimeClasspath += main.get().runtimeClasspath

        java.srcDirs += rootProject.file("src/main/java")
        kotlin.srcDirs += rootProject.file("src/main/kotlin")
        resources.srcDirs += rootProject.file("src/main/resources")
    }
}

loom {
    runs {
        create("testmodClient") {
            client()
            name("Testmod Client")
            source(sourceSets.getByName("testmod"))
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.21.11:${project.property("parchment_version")}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${project.property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_language_version")}")

    implementation(include("com.github.weisj:jsvg:${project.property("jsvg_version")}")!!)

    "testmodImplementation"(sourceSets["main"].output)
}

tasks.withType<ProcessResources>().configureEach {
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to project.property("minecraft_version") as String,
            "fabric_loader_version" to project.property("fabric_loader_version") as String,
            "fabric_api_version" to project.property("fabric_api_version") as String,
            "kotlin_language_version" to project.property("kotlin_language_version") as String
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}