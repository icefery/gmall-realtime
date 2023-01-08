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
    compileOnly("org.apache.flink:flink-table-planner_2.12:1.13.6")
    compileOnly("org.apache.flink:flink-clients_2.12:1.13.6")
    implementation("com.ververica:flink-sql-connector-mysql-cdc:2.2.1")
    implementation("org.apache.flink:flink-connector-kafka_2.11:1.13.6")
}

sourceSets {
    main {
        runtimeClasspath += project.configurations.compileClasspath.get()
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations.runtimeClasspath.get())
}
