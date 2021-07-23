import com.soywiz.korge.gradle.*

group = "org.rak.manapart"
version = ""

buildscript {
    val korgePluginVersion: String by project

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
//        commonMainApi("com.soywiz.korlibs.korge:korge-box2d:$korgeVersion")
    }
}
apply<KorgeGradlePlugin>()


plugins {
    kotlin("plugin.serialization") version "1.5.20"
}

korge {
    id = "org.rak.manapart"
    targetJvm()
    targetJs()
    serializationJson()
}