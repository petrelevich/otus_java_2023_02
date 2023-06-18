plugins {
    id ("com.github.johnrengelman.shadow")
    id ("maven-publish")
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    compileOnly ("org.slf4j:slf4j-api")
    implementation ("org.springframework.boot:spring-boot-configuration-processor")

    implementation ("ru.otus:messager:2.0")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "ru.otus"
            artifactId = "messager-starter"
            version = "2.0"
            from(components["java"])
        }
    }
}

tasks {
    build {
        dependsOn(publishToMavenLocal)
    }
}
