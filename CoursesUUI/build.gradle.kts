plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "tech.miam.coursesuui"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk =33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        kotlinCompilerExtensionVersion = "1.4.0"
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
    api ("tech.miam.sdk:kmm-miam-sdk:3.12.14")
    api ("androidx.core:core-ktx:1.10.1")
    api ("androidx.appcompat:appcompat:1.6.1")
    api ("com.google.android.material:material:1.9.0")
    api ("androidx.compose.material:material:1.4.3")
    api ("androidx.compose.compiler:compiler:1.4.8")
    api ("androidx.compose.ui:ui-tooling:1.4.3")
    api ("androidx.compose.ui:ui:1.4.3")
    api ("androidx.compose.foundation:foundation:1.4.3")
    api ("androidx.compose.material:material-icons-core:1.4.3")
    api ("androidx.compose.material:material-icons-extended:1.4.3")
    api ("androidx.compose.runtime:runtime-rxjava2:1.4.3")
    api ("io.coil-kt:coil-compose:1.3.1")
    api ("io.coil-kt:coil-svg:1.3.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}

val PUBLISH_GROUP_ID by extra("io.github.miamtech")
val PUBLISH_ARTIFACT_ID by extra { "miamCoursesUUI" }

apply("${rootDir}/scripts/publish-module.gradle")

