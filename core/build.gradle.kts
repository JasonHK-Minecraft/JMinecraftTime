group = "dev.jasonhk.mcrtc.core"
version = "0.0.1"

plugins {
    id("io.freefair.lombok")
    id("java")
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}