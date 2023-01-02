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
    }
}
apply<KorgeGradlePlugin>()


plugins {
    kotlin("plugin.serialization") version "1.5.20"
}

korge {
    id = "org.rak.manapart"
//    supportBox2d()

//    bundle("https://github.com/korlibs/korge-bundles.git::korge-box2d::7439e5c7de7442f2cd33a1944846d44aea31af0a##9fd9d54abd8abc4736fd3439f0904141d9b6a26e9e2f1e1f8e2ed10c51f490fd")

    targetJvm()
    targetJs()
    serializationJson()
}