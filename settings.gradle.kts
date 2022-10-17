rootProject.name = "glow"

includePrefixed("api")
includePrefixed("adapt")
includePrefixed("plugin")

arrayOf("1_19_R1").forEach {
    includePrefixed("adapt:v$it")
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

fun includePrefixed(name: String) {
    val kebabName = name.replace(':', '-')
    val path = name.replace(':', '/')
    val baseName = "${rootProject.name}-$kebabName"

    include(baseName)
    project(":$baseName").projectDir = file(path)
}