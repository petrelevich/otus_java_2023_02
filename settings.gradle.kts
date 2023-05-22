rootProject.name = "otusJava"
include("L01-gradle")

include("L02-gradle2")
include("L02-gradle2-libApi")
include("L02-gradle2-libApiUse")
include("L03-qa")
include("L04-generics")
include("L05-collections")
include("L06-annotations")

include("L08-gc:demo")
include("L08-gc:homework")

include("L09-docker")
include("L10-byteCodes")
include("L11-java8")

include("L12-solid")
include("L13-creationalPatterns")
include("L14-behavioralPatterns")
include("L15-structuralPatterns:demo")
include("L15-structuralPatterns:homework")

include("L16-io:demo")
include("L16-io:homework")

include("L17-nio-logging")

include("L18-rdbms")

include("L19-jdbc:demo")
include("L19-jdbc:homework")

include ("L20-hibernate")

include ("L21-jpql:class-demo")
include ("L21-jpql:homework-template")


pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
    }
}