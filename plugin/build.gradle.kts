plugins {
    id("glow.common-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

dependencies {
    implementation(project(":glow-api"))
    implementation(project(":glow-adapt"))
    compileOnly(libs.spigot)
}

val pluginName = "Glow"

bukkit {
    name = pluginName
    apiVersion = "1.13"
    main = "${rootProject.group}.${pluginName.toLowerCase()}.${pluginName}Plugin"
    description = "Plugin to test glow api from Unnamed"
    authors = listOf("pixeldev")
    commands {
        register("glow") {
            description = "Glow command"
            permission = "glow.command.glow"
            permissionMessage = "§cYou don't have permission to execute this command."
        }
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("glow")
        archiveClassifier.set("")

        // special subprojects that require reobfuscation
        arrayOf("v1_19_R1").forEach {
            val buildTask = project(":${rootProject.name}-adapt-$it").tasks.named("reobfJar")
            dependsOn(buildTask)
            from(zipTree(buildTask.map { out -> out.outputs.files.singleFile }))
        }
    }
}