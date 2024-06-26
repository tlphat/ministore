plugins {
    application
    checkstyle
    idea
    jacoco
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

    testImplementation("org.awaitility:awaitility:4.2.1")
    testImplementation("org.awaitility:awaitility-proxy:3.1.6")
}

checkstyle {
    toolVersion = "10.16.0"
    configDirectory.set(file("config/checkstyle"))
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

/**
 * Configure integration test source set
 */
val integrationTest: SourceSet = sourceSets.create("integrationTest") {
    java {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        srcDir("src/integration-test/java")
    }
    resources.srcDir("src/integration-test/resources")
}

configurations[integrationTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[integrationTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())
configurations[integrationTest.annotationProcessorConfigurationName].extendsFrom(configurations.testAnnotationProcessor.get())

val integrationTestTask = tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    useJUnitPlatform()

    testClassesDirs = integrationTest.output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
}

idea {
    module {
        testSources.from(integrationTest.java.srcDirs)
    }
}

/**
 * Configure test coverage report
 */
val jacocoTestReport by tasks.getting(JacocoReport::class) {
    dependsOn(tasks.test, integrationTestTask)

    reports {
        html.required.set(true)
        xml.required.set(true)
    }

    // Include coverage results from both unit tests and integration tests
    executionData(tasks.test.get(), integrationTestTask.get())

    afterEvaluate {
        // Define patterns excluded from test coverage
        classDirectories.setFrom(files(classDirectories.files.map { file ->
            fileTree(file).apply {
                exclude(
                        "name/tlphat/ministore/server/Application**"
                )
            }
        }))
    }
}
