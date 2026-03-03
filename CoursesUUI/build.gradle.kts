plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "tech.miam.coursesuui"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lint {
        abortOnError = false
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf(
            "-Xjvm-default=all",
            "-opt-in=coil.annotation.ExperimentalCoilApi"
        )
    }
    buildToolsVersion = "33.0.2"
}

dependencies {
    api("ai.mealz.android:mealz-android:6.0.1-alpha1")
    // Remove these following dependencies with the upgarde to 6.1.x version. Currently 6.0.0 wasn't built with them defined as api :/
    //api("org.jetbrains.kotlinx:atomicfu:0.27.0")
    //api("io.insert-koin:koin-android:3.5.3")
    //api("androidx.webkit:webkit:1.14.0")

    api("androidx.core:core-ktx:1.17.0")
    api("androidx.appcompat:appcompat:1.7.1")
    api("com.google.android.material:material:1.9.0")
    api("androidx.compose.material:material:1.9.0")
    api("androidx.compose.compiler:compiler:1.5.15")
    api("androidx.compose.ui:ui-tooling:1.9.0")
    api("androidx.compose.ui:ui:1.9.0")
    api("androidx.compose.foundation:foundation:1.9.0")
    api("androidx.compose.material:material-icons-core:1.7.8")
    api("androidx.compose.material:material-icons-extended:1.7.8")
    api("androidx.compose.runtime:runtime-rxjava2:1.9.0")
    api("io.coil-kt:coil-compose:2.2.0")
    api("io.coil-kt:coil-svg:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

}

val PUBLISH_GROUP_ID by extra("io.github.miamtech")
val PUBLISH_ARTIFACT_ID by extra { "miamCoursesUUI" }

apply("${rootDir}/scripts/publish-module.gradle")

tasks.matching { it.name == "generateMetadataFileForReleasePublication" }.configureEach {
    dependsOn(tasks.named("androidSourcesJar"))
}
tasks.named("androidSourcesJar").configure {
    enabled = false
}
