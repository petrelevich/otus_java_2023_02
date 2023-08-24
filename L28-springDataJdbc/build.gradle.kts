dependencies {

    implementation("ch.qos.logback:logback-classic")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("com.google.code.findbugs:jsr305")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.springframework:spring-test:6.0.11")
    testImplementation("org.springframework.boot:spring-boot-test:3.1.2")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure:3.1.2")
}
