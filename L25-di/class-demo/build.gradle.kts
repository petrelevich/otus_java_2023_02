dependencies {
    implementation("ch.qos.logback:logback-classic")

    implementation("org.reflections:reflections")
    implementation("org.springframework:spring-context")

    testImplementation("org.springframework:spring-test")

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}