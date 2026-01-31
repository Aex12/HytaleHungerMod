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
    compileOnly(fileTree("libs") { include("*.jar") })
    compileOnly("com.google.code.gson:gson:2.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Minimal Hytale manifest generation
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
              "Authors": [ 
                  { "Name": "jume" },
                  { "Name": "andiemg" },
                  { "Name": "antigravity" }
              ],
              "IncludesAssetPack": true
            }
        """.trimIndent()
        
        outputDir.get().file("manifest.json").asFile.apply {
            parentFile.mkdirs()
            writeText(json)
        }
    }
}

sourceSets {
    main {
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
