

plugins {
    id ("com.android.application") version "8.0.1" apply false
    id ("com.android.library") version "8.0.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("maven-publish")
    id("signing")
}


/**
 * Maven central deploy script
 * */

publishing {
    repositories {
        maven {
            name = "Oss"
            setUrl {
                 val repositoryId =
                        System.getenv("SONATYPE_REPOSITORY_ID") ?: error("Missing env variable: SONATYPE_REPOSITORY_ID")
                "https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/${repositoryId}/"
            }
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
        maven {
            name = "Snapshot"
            setUrl { "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }

    publications {
        withType<MavenPublication> {
            pom {
                name.set("kmm-miam-sdk")
                description.set("Miam SDK is a component libray that is directly wired with miam API.  It allow you to inject recipe into your mobile App")
                url.set("https://github.com/miamtech/miam-sdk")
                licenses {
                    license {
                        name.set("Miam License")
                        url.set("https://github.com/miamtech/miam-sdk/blob/main/LICENSE")
                    }
                }
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/miamtech/miam-sdk/issues")
                }
                scm {
                    connection.set("https://github.com/miamtech/miam-sdk.gits")
                    url.set("https://github.com/miamtech/miam-sdk/tree/main")
                    developerConnection.set("git@github.com:miamtech/miam-sdk.git")
                }
                developers {
                    developer {
                        name.set("thibault corbrion")
                        email.set("thibault.corbrion@miam.tech")
                    }
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD")
    )
    sign(publishing.publications)
}