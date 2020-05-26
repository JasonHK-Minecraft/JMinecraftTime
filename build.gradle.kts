group = "dev.jasonhk.mcrtc"
version = "0.0.1"

allprojects {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

plugins {
    id("io.freefair.lombok") version "5.1.0" apply false
    id("java")
}