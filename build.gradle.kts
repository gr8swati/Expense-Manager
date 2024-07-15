// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.realm:realm-gradle-plugin:10.18.0")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}

