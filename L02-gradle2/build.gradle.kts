import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
}


dependencies {
    implementation("com.google.guava:guava")
    implementation("com.zaxxer:HikariCP")

    implementation("org.springframework.data:spring-data-hadoop:2.0.0.RELEASE")
    implementation("org.springframework:spring-tx:3.2.3.RELEASE")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloWorld")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.App"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
