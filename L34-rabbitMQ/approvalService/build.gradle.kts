dependencies {
    implementation(project(":L34-rabbitMQ:allServicesModels"))

    implementation("ch.qos.logback:logback-classic")
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation ("jakarta.persistence:jakarta.persistence-api")
}