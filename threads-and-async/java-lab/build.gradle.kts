plugins {
    id("java")
}

group = "com.yilmaznaslan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Ensure JUnit Platform launcher is available on the test runtime classpath so Gradle can start the JUnit Platform.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.0")
    
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}


