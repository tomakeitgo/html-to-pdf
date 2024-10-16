plugins {
    id("java")
}

group = "com.tomakeitgo.html-to-pdf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

dependencies {
    implementation(project(":converter-request"))
    implementation("com.itextpdf:html2pdf:5.0.5")
    implementation("com.github.librepdf:openpdf:2.0.3")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}