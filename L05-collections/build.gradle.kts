plugins {
    id ("java")
}

dependencies {
    testImplementation ("org.openjdk.jmh:jmh-core")
    testAnnotationProcessor ("org.openjdk.jmh:jmh-generator-annprocess")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.assertj:assertj-core")
}
