dependencies {
    implementation(project(":L34-rabbitMQ:allServicesModels"))

    implementation("ch.qos.logback:logback-classic")
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("com.h2database:h2")
}