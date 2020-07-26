import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.3.72" apply false
    kotlin("plugin.spring") version "1.3.72" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
}

group = "io.t28.springframework.social"
version = "1.0.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    ktlint {
        version.set("0.37.2")
        filter {
            include("src/main/kotlin/**")
            exclude("**/generated/**")
        }
        reporters {
            reporter(ReporterType.PLAIN)
        }
    }
}
