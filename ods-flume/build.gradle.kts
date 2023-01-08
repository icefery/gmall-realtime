plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "xyz.icefery.demo"
version = "0.0.1"
java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.apache.flume:flume-ng-core:1.9.0")
}

sourceSets {
    main {
        runtimeClasspath += project.configurations.compileClasspath.get()
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations.runtimeClasspath.get())
}
