

plugins {
    id ("com.android.application") version "8.0.1" apply false
    id ("com.android.library") version "8.0.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.appdistribution") version "4.0.0" apply false
}

buildscript {
    dependencies {
        classpath ("io.github.gradle-nexus:publish-plugin:1.1.0")
    }
}

/**
 * Maven central deploy script
 * */
apply("${rootDir}/scripts/publish-root.gradle")
