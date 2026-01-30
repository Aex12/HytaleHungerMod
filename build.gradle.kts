plugins {
    id("java")
}

group = "es.xcm"
version = "0.1.17"

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

/**
 * Generate manifest.json content as a generated resource
 * using Gradle's group/name/version before every build/processResources.
 */
val generatedResourcesDir: Provider<Directory> = layout.buildDirectory.dir("generated/resources/main")

val generateManifest by tasks.registering {
    val outFile = generatedResourcesDir.map { it.file("manifest.json") }

    // Regenerate if any of these change
    inputs.property("group", project.group.toString())
    inputs.property("name", rootProject.name)
    inputs.property("version", project.version.toString())

    outputs.file(outFile)

    doLast {
        val f = outFile.get().asFile
        f.parentFile.mkdirs()

        val json = """
            {
              "Group": "${project.group}",
              "Name": "${rootProject.name}",
              "Version": "${project.version}",
              "Website": "https://www.curseforge.com/hytale/mods/hungry",
              "Main": "es.xcm.hunger.HytaleHungerMod",
              "IncludesAssetPack": true
            }
        """.trimIndent() + "\n"

        f.writeText(json, Charsets.UTF_8)
    }
}

// Make Gradle include the generated resources directory in the main resources
sourceSets {
    named("main") {
        resources.srcDir(generatedResourcesDir)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

// Ensure it's generated before resources are packaged (build/jar/run/etc.)
tasks.named<ProcessResources>("processResources") {
    dependsOn(generateManifest)
}
