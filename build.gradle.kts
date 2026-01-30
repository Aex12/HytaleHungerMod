plugins {
    id("java")
}

group = "mx.jume.aquahunger"
version = "0.1.19"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Standard practice for local jar dependencies in Hytale mods
    compileOnly(fileTree("libs") { include("*.jar") })
    
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

/**
 * Task to generate the Hytale manifest.json metadata file.
 * Following Gradle best practices for task inputs/outputs and lazy properties.
 */
val generateManifest by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/resources")
    outputs.dir(outputDir)
    
    inputs.property("group", project.group)
    inputs.property("name", rootProject.name)
    inputs.property("version", project.version)

    doLast {
        val json = """
            {
              "Group": "${project.group}",
              "Name": "${rootProject.name}",
              "Version": "${project.version}",
              "Main": "${project.group}.AquaThirstHunger",
              "IncludesAssetPack": true
            }
        """.trimIndent()
        
        val manifestFile = outputDir.get().file("manifest.json").asFile
        manifestFile.parentFile.mkdirs()
        manifestFile.writeText(json)
    }
}

sourceSets {
    main {
        // Automatically wires generateManifest as a dependency of processResources
        resources.srcDir(generateManifest)
    }
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to rootProject.name,
            "Implementation-Version" to project.version,
            "Main-Class" to "${project.group}.AquaThirstHunger"
        )
    }
}
