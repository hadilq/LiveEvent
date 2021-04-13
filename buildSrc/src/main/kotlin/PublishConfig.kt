import org.gradle.api.Action
import com.android.build.gradle.internal.dsl.InternalLibraryExtension
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

const val SNAPSHOT = "-SNAPSHOT"
const val GROUP_ID = "com.github.hadilq"
const val VERSION = "1.0.2"
//const val LIB_VERSION = VERSION
val LIB_VERSION = "$VERSION.${System.currentTimeMillis()}$SNAPSHOT"

fun isSnapshot(version: String): Boolean = version.endsWith(SNAPSHOT)

fun Project.setupPublication() {
    plugins.apply("org.jetbrains.dokka")
    plugins.apply("maven-publish")
    plugins.apply(SigningPlugin::class.java)

    group = GROUP_ID
    version = LIB_VERSION

    val userId = "hadilq"
    val userName = "Hadi Lashkari Ghouchani"
    val userEmail = "hadilq.dev@gmail.com"
    val githubUrl = "https://github.com/hadilq/LiveEvent"
    val githubScmUrl = "scm:git@github.com:hadilq/LiveEvent.git"

    val ossrhUsername: String? = System.getenv()["OSSRH_USERNAME"]
        ?: findProperty("ossrhUsername") as String?
    val ossrhPassword: String? = System.getenv()["OSSRH_PASSWORD"]
        ?: findProperty("ossrhPassword") as String?

    val javadocJar by tasks.registering(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        archiveClassifier.value("javadoc")
        from(tasks.getByName("dokkaJavadoc"))
    }

    val sourcesJar by tasks.registering(Jar::class) {
        archiveClassifier.value("sources")
        from(android.sourceSets.getByName("main").java.srcDirs)
    }

    publishing {
        publications {
            register("maven", MavenPublication::class) {
                artifact(sourcesJar.get())
                artifact(javadocJar)
                if (!isSnapshot(version)) {
                    signing.sign(this)
                }
                pom {
                    withXml {
                        asNode().apply {
                            appendNode("name", "LiveEvent")
                            appendNode(
                                "description",
                                "This library holds a class to handle single live events in Android MVVM architectural pattern. This class is extended " +
                                        "form LiveData class, from `androidx.lifecycle:lifecycle-extensions` library, to propagate the data as an event, " +
                                        "which means it emits data just once."
                            )
                            appendNode("url", githubUrl)
                        }
                    }
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set(userId)
                            name.set(userName)
                            email.set(userEmail)
                        }
                    }
                    scm {
                        url.set(githubUrl)
                        connection.set(githubScmUrl)
                        developerConnection.set(githubScmUrl)
                    }
                }
            }
        }

        repositories {
            maven {
                url = if (isSnapshot("$version"))
                    uri("https://oss.sonatype.org/content/repositories/snapshots/")
                else
                    uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }

    }
}

/**
 * Retrieves the [signing][SigningExtension] extension.
 */
val Project.signing: SigningExtension
    get() = (this as ExtensionAware).extensions.getByName("signing") as SigningExtension

/**
 * Retrieves the [sourceSets][SourceSetContainer] extension.
 */
val Project.sourceSets: SourceSetContainer
    get() = (this as ExtensionAware).extensions.getByName("sourceSets") as SourceSetContainer

/**
 * Provides the existing [main][SourceSet] element.
 */
val SourceSetContainer.main: NamedDomainObjectProvider<SourceSet>
    get() = named<SourceSet>("main")

/**
 * Configures the [publishing][PublishingExtension] extension.
 */
fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    (this as ExtensionAware).extensions.configure("publishing", configure)

/**
 * Retrieves the [android][InternalLibraryExtension] extension.
 */
val Project.android: InternalLibraryExtension
    get() =
        (this as ExtensionAware).extensions.getByName("android") as InternalLibraryExtension
