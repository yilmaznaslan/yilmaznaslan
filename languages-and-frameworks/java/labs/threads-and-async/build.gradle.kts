plugins {
    id("java")
    id("application")
}

group = "com.yilmaznaslan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass.set("com.yilmaznaslan.threads.basics.ThreadBasicsExample")
}

tasks.register<JavaExec>("runExample") {
    group = "application"
    description = "Run a specific example class. Usage: ./gradlew runExample -PmainClass=com.yilmaznaslan.threads.basics.ThreadBasicsExample"
    
    val mainClassProp = project.findProperty("mainClass") as String?
    if (mainClassProp != null) {
        mainClass.set(mainClassProp)
    } else {
        mainClass.set(application.mainClass.get())
    }
    
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runMemoryIntensiveWithJFR") {
    group = "jfr"
    description = "Run MemoryIntensiveExample with JFR enabled for 30 seconds"
    
    mainClass.set("com.yilmaznaslan.threads.jfr.MemoryIntensiveExample")
    classpath = sourceSets["main"].runtimeClasspath
    
    val jfrDir = layout.buildDirectory.dir("jfr").get().asFile
    val recordingFile = jfrDir.resolve("memory-intensive-recording.jfr")
    
    jvmArgs = listOf(
        "-XX:+FlightRecorder",
        "-XX:StartFlightRecording=duration=30s,filename=${recordingFile.absolutePath}"
    )
    
    doFirst {
        jfrDir.mkdirs()
    }
}

tasks.register<JavaExec>("runWithJFR") {
    group = "jfr"
    description = "Run any class with JFR enabled. Usage: ./gradlew runWithJFR -PmainClass=com.yilmaznaslan.threads.basics.ThreadBasicsExample -Pduration=60s"
    
    val mainClassProp = project.findProperty("mainClass") as String?
    if (mainClassProp != null) {
        mainClass.set(mainClassProp)
    } else {
        mainClass.set(application.mainClass.get())
    }
    
    val duration = project.findProperty("duration") as String? ?: "60s"
    val recordingName = mainClass.get().substringAfterLast(".").lowercase()
    
    val jfrDir = layout.buildDirectory.dir("jfr").get().asFile
    val recordingFile = jfrDir.resolve("${recordingName}-recording.jfr")
    
    classpath = sourceSets["main"].runtimeClasspath
    
    jvmArgs = listOf(
        "-XX:+FlightRecorder",
        "-XX:StartFlightRecording=duration=$duration,filename=${recordingFile.absolutePath}"
    )
    
    doFirst {
        jfrDir.mkdirs()
    }
}

