plugins {
    id ("org.springframework.boot")
}

dependencies {

    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui")

    implementation ("ru.otus:messager-starter:2.0:all")
    /*
        implementation ("org.mongodb:mongo-java-driver:3.12.2")
        implementation ("org.mongodb:mongodb-driver:3.12.2")
    */
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}


