group = "me.ryguy"
version = "1.0-SNAPSHOT"
description = "Parent gradle for all small plugin sub projects"

plugins {
    java
}

subprojects {
    repositories {
        maven("https://oss.sonatype.org/content/repositories/central")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
    }
}