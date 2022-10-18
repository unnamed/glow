plugins {
    id("glow.publishing-conventions")
}

dependencies {
    api(project(":glow-api"))
    compileOnly(libs.spigot)
}