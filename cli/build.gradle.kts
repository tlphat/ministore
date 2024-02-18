plugins {
    application
}

group = "name.tlphat.ministore"

application {
    mainClass.set("name.tlphat.ministore.cli.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass
        attributes["Class-Path"] = configurations.runtimeClasspath.get().files.joinToString(" ")
    }
}

tasks.test {
    useJUnitPlatform()
}
