import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    id("fr.brouillard.oss.gradle.jgitver")
    id("io.spring.dependency-management")
    id("org.springframework.boot") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(17)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}


allprojects {
    group = "ru.otus"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val testcontainersBom: String by project
    val protobufBom: String by project
    val guava: String by project
    val jmh: String by project
    val asm: String by project
    val glassfishJson: String by project
    val ehcache: String by project

    val lombok: String by project
    val gson: String by project

    val mongodb: String by project
    val mongodbReactive: String by project
    val cassandra: String by project
    val neo4j: String by project
    val jedis: String by project

    val jetty: String by project
    val freemarker: String by project


    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
                mavenBom("org.testcontainers:testcontainers-bom:$testcontainersBom")
                mavenBom("com.google.protobuf:protobuf-bom:$protobufBom")
            }
            dependency("com.google.guava:guava:$guava")
            dependency("org.openjdk.jmh:jmh-core:$jmh")
            dependency("org.openjdk.jmh:jmh-generator-annprocess:$jmh")
            dependency("org.ow2.asm:asm-commons:$asm")
            dependency("org.glassfish:jakarta.json:$glassfishJson")
            dependency("org.ehcache:ehcache:$ehcache")

            dependency("org.projectlombok:lombok:$lombok")
            dependency("com.google.code.gson:gson:$gson")
            dependency("com.datastax.oss:java-driver-core:$cassandra")

            dependency("org.mongodb:mongodb-driver-core:$mongodb")
            dependency("org.mongodb:mongodb-driver-sync:$mongodb")
            dependency("org.mongodb:bson:$mongodb")
            dependency("org.mongodb:mongodb-driver-reactivestreams:${mongodbReactive}")
            dependency("org.neo4j.driver:neo4j-java-driver:$neo4j")
            dependency("redis.clients:jedis:$jedis")

            dependency("org.eclipse.jetty:jetty-servlet:$jetty")
            dependency("org.eclipse.jetty:jetty-server:$jetty")
            dependency("org.eclipse.jetty:jetty-webapp:$jetty")
            dependency("org.eclipse.jetty:jetty-security:$jetty")
            dependency("org.eclipse.jetty:jetty-http:$jetty")
            dependency("org.eclipse.jetty:jetty-io:$jetty")
            dependency("org.eclipse.jetty:jetty-util:$jetty")
            dependency("org.freemarker:freemarker:$freemarker")


        }
    }
    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()

            force("javax.servlet:servlet-api:2.4")
            force("commons-logging:commons-logging:1.1.1")
            force("commons-lang:commons-lang:2.5")
            force("org.codehaus.jackson:jackson-core-asl:1.8.8")
            force("org.codehaus.jackson:jackson-mapper-asl:1.8.3")
            force("org.codehaus.jettison:jettison:1.1")
            force("net.java.dev.jna:jna:5.8.0")
            force("com.google.errorprone:error_prone_annotations:2.7.1")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing")) //, "-Werror"))
    }

    plugins.apply(fr.brouillard.oss.gradle.plugins.JGitverPlugin::class.java)
    extensions.configure<fr.brouillard.oss.gradle.plugins.JGitverPluginExtension> {
        strategy("PATTERN")
        nonQualifierBranches("main,master")
        tagVersionPattern("\${v}\${<meta.DIRTY_TEXT}")
        versionPattern(
            "\${v}\${<meta.COMMIT_DISTANCE}\${<meta.GIT_SHA1_8}" +
                    "\${<meta.QUALIFIED_BRANCH_NAME}\${<meta.DIRTY_TEXT}-SNAPSHOT"
        )
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
}

tasks {
    val hello by registering {
        doLast {
            println("hello task")
        }
    }

    val managedVersions by registering {
        doLast {
            project.extensions.getByType<DependencyManagementExtension>()
                .managedVersions
                .toSortedMap()
                .map { "${it.key}:${it.value}" }
                .forEach(::println)
        }
    }
}