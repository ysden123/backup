plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

version = "1.0.1"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.typesafe/config
    implementation("com.typesafe:config:1.4.2")
    // https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.11.0")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    // Define the main class for the application.
    mainClass.set("com.stulsoft.backup.App")
    applicationDefaultJvmArgs = listOf("-Dconfig.file=application.conf")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.register<Copy>("copyRunWithPause"){
    from(layout.projectDirectory.file("runWithPause.bat"))
    into(layout.buildDirectory.dir("scripts"))
}

tasks.named("startScripts"){finalizedBy("copyRunWithPause")}
