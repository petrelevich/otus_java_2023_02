plugins {
    id ("java")
    id ("org.springframework.boot")
    id ("com.google.cloud.tools.jib")
    id ("fr.brouillard.oss.gradle.jgitver")
}



dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.flywaydb:flyway-core")
    implementation ("org.postgresql:postgresql")

    testImplementation ("org.testcontainers:testcontainers")
    testImplementation ("org.testcontainers:postgresql")
    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.assertj:assertj-core")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

jib {
    container {
        creationTime.set("USE_CURRENT_TIMESTAMP")
    }
    from {
        image = "bellsoft/liberica-openjdk-alpine-musl:17.0.2-9"
    }

    to {
        image = "registry.gitlab.com/petrelevich/dockerregistry/rest-hello"
        tags = setOf(project.version.toString())
        auth {
            username = System.getenv("GITLAB_USERNAME")
            password = System.getenv("GITLAB_PASSWORD")
        }
    }
}
/*
сборка проекта:

./gradlew :L09-docker:build
публикация:
./gradlew :L09-docker:jib
*/
