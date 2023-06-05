dependencies {
    implementation("ch.qos.logback:logback-classic")
    implementation("com.google.code.gson:gson")

    implementation("org.eclipse.jetty:jetty-servlet")
    implementation("org.eclipse.jetty:jetty-server")
    implementation("org.eclipse.jetty:jetty-webapp")
    implementation("org.eclipse.jetty:jetty-security")
    implementation("org.eclipse.jetty:jetty-http")
    implementation("org.eclipse.jetty:jetty-io")
    implementation("org.eclipse.jetty:jetty-util")
    implementation("org.freemarker:freemarker")

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}