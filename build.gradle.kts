plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.26.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    testImplementation("org.testng:testng:7.10.2")
}

group = "org.example"
version = "1.0-SNAPSHOT"
description = "Selenium-Webdriver-3-Advanced-Task"
java.sourceCompatibility = JavaVersion.toVersion("21")
java.targetCompatibility = JavaVersion.toVersion("21")

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

val defaultBrowser = "chrome"
val browser = project.findProperty("browser")?.toString() ?: defaultBrowser

val defaultGridUrl = "http://localhost:4444"
val gridUrl = project.findProperty("gridUrl")?.toString() ?: defaultGridUrl

val tags: String = project.findProperty("tags")?.toString() ?: ""

tasks.register("runTests", Test::class) {
    useTestNG {
        suites("src/test/resources/regression.xml")

        if (tags.isNotEmpty()) {
            includeGroups(tags)
        }
        setParallel("methods")
        threadCount = 2
    }

    systemProperty("browser", browser)
    systemProperty("gridUrl", gridUrl)
}


tasks.named<Test>("test") {
    useTestNG {
        suites("src/test/resources/regression.xml")
        setParallel("methods")
        threadCount = 2
    }
}
