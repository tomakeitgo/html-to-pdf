plugins {
    id("java")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

group = "com.tomakeitgo.html-to-pdf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":converter"))

    implementation("org.eclipse.jetty:jetty-server:12.0.14")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-webapp:12.0.14")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}