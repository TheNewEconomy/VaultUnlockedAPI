plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/groups/public/")
    }
}

dependencies {
    // See ./gradle/libs.versions.toml
    api(libs.org.jetbrains.annotations)
    testImplementation(libs.junit.junit)
    compileOnly(libs.org.bukkit.bukkit)
}

group = "net.milkbowl.vault"
version = "2.15"
description = "VaultUnlockedAPI"
java.sourceCompatibility = JavaVersion.VERSION_1_8

java {
    withSourcesJar()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
