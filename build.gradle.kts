import org.gradle.api.tasks.testing.logging.TestLogEvent;

group = "dev.jasonhk.minecraft.time"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

plugins {
    java
    jacoco

    id("io.freefair.lombok") version "5.1.0"
}

dependencies {
    implementation("org.apiguardian", "apiguardian-api", "1.1.0")

    testImplementation("org.assertj", "assertj-core", "3.16.1")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

tasks.register("testCoverage") {
    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestReport)
}
