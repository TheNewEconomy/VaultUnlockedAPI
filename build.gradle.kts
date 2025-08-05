plugins {
    `java-library`
    `maven-publish`
}

group = "net.milkbowl.vault"
version = "2.15"
description = "VaultUnlockedAPI"

repositories {
    mavenLocal()
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/groups/public/")
    }
}

dependencies {
    // See ./gradle/libs.versions.toml for updating package groups and versions.

    //Runtime
    compileOnly(libs.annotations.jetbrains)
    compileOnly(libs.bukkit) {
        exclude("com.google.code.gson", "gson")
        exclude("com.google.guava", "guava")
        exclude("commons-lang", "commons-lang")
        exclude("junit", "junit")
        exclude("org.yaml", "snakeyaml")
    }

    //Tests
    testImplementation(libs.junit)
}



java {
    withSourcesJar()
    withJavadocJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])

        pom {
            name = rootProject.name
            description = "VaultUnlocked is a Permissions & Economy API to allow plugins to more easily hook into" +
                    " these systems without needing to hook each individual system themselves. " +
                    "VaultUnlocked supports all plugins that support Vault 1.7"
            url = "https://dev.bukkit.org/server-mods/vault/"
            organization {
                name = "The New Economy"
                url = "https://tnemc.net"
            }
            licenses {
                license {
                    name = "GNU Lesser General Public License, Version 3 (LGPL-3.0)"
                    url = "https://github.com/TheNewEconomy/VaultUnlockedAPI/blob/master/license.txt"
                }
            }
            developers {
                developer {
                    id = "creatorfromhell"
                    name = "Daniel \"creatorfromhell\" Vidmar"
                    email = "daniel.viddy@gmail.com"
                    url = "https://cfh.dev"
                    organization = "The New Economy"
                    organizationUrl = "https://tnemc.net"
                }
            }
            scm {
                connection = "scm:git:git://github.com/TheNewEconomy/VaultUnlockedAPI.git"
                developerConnection = "scm:git:git://github.com/TheNewEconomy/VaultUnlockedAPI.git"
                url = "https://github.com/TheNewEconomy/VaultUnlockedAPI"
            }
            issueManagement {
                system = "GitHub"
                url = "https://github.com/TheNewEconomy/VaultUnlockedAPI/issues"
            }
        }
    }

    repositories {
        maven {
            name = "CodeMC"
            url = uri("https://repo.codemc.io/repository/creatorfromhell/")

            val mavenUsername = System.getenv("GRADLE_PROJECT_MAVEN_USERNAME")
            val mavenPassword = System.getenv("GRADLE_PROJECT_MAVEN_PASSWORD")
            if (mavenUsername != null && mavenPassword != null) {
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Javadoc> {
    isFailOnError = false

    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).apply {
        windowTitle = "VaultUnlocked"
        isAuthor = true
        isVersion = true
        links ("https://docs.oracle.com/javase/8/docs/api/", "")
        bottom = "<b>TheNewEconomy, 2025</b>"
        isNoTimestamp = true
    }
}
