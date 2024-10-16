plugins {
    id("org.gradle.toolchains.foojay-resolver") version "0.8.0"
}

toolchainManagement {
    jvm {
        javaRepositories {
            repository("foojay") {
                resolverClass.set(org.gradle.toolchains.foojay.FoojayToolchainResolver::class.java)
            }
        }
    }
}

rootProject.name = "html-to-pdf"

include("converter")
include("converter-request")
include("http-server")
include("http-client")