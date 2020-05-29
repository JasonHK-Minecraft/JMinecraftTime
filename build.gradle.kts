group = "dev.jasonhk.mctime"
version = "0.0.1"

allprojects {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

plugins {
    id("io.freefair.lombok") version "5.1.0"
    id("java")
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}