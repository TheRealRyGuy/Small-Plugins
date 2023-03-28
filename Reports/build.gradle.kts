plugins {
    `java`
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8-R0.1-SNAPSHOT")
}

group = "me.ryguy"
version = "1.0-SNAPSHOT"
description = "Be able to report and chat report players!"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}