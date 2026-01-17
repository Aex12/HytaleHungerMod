plugins {
    id("java")
}

group = "es.xcm"
version = "0.1.2"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(fileTree("libs") { include("*.jar") })
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}