plugins {
    id("com.github.johnrengelman.shadow")
    id("maven-publish")
}

dependencies {

}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "ru.otus"
            artifactId = "messager"
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
