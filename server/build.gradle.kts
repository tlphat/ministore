plugins {
    application
}

group = "name.tlphat.ministore"

application {
    mainClass.set("name.tlphat.ministore.server.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.11")
    implementation("org.slf4j:slf4j-simple:2.0.11")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}

tasks.test {
    useJUnitPlatform()
}
