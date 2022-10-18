plugins {
    id("glow.publishing-conventions")
    id("io.papermc.paperweight.userdev") version "1.3.5"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}

dependencies {
    api(project(":glow-adapt"))
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
}