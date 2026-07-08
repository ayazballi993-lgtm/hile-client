plugins {
    id("fabric-loom") version "1.8.12"
    kotlin("jvm") version "2.0.0"
}

version = "1.0.0"
group = "com.hileclient"

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.9")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.12.3+kotlin.2.0.0")
}

kotlin {
    jvmToolchain(21)
}
