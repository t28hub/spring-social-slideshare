/*
 * Copyright 2020 Tatsuya Maki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.lang.System.getenv
import java.util.Properties

plugins {
    java
    jacoco
    kotlin("jvm") version "1.3.72" apply false
    kotlin("kapt") version "1.3.72" apply false
    kotlin("plugin.spring") version "1.3.72" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.10.0"
    id("info.solidsoft.pitest") version "1.5.1"
}

val properties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

fun getProperty(key: String, defaultValue: String?): String? {
    return properties.getProperty(key, defaultValue)
}

val getPropertyInternal: (key: String, defaultValue: String?) -> String? = ::getProperty

val getProperty by extra {
    getPropertyInternal
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://repo.spring.io/libs-snapshot")
        maven {
            url = uri("https://maven.pkg.github.com/t28hub/auto-truth")
            credentials {
                username = getProperty("GITHUB_ACTOR", getenv("GITHUB_ACTOR"))
                password = getProperty("GITHUB_TOKEN", getenv("GITHUB_TOKEN"))
            }
        }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "jacoco")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "info.solidsoft.pitest")

    group = "io.t28.springframework.social"
    version = "1.0.0-SNAPSHOT"

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // https://github.com/spring-projects/spring-boot/issues/11594
    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:2.3.0.RELEASE")
        }
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

    detekt {
        input = files("src/main/kotlin")
        config = files("${project.rootDir}/config/detekt.yml")
        reports {
            html {
                enabled = true
                destination = file("$buildDir/reports/detekt/index.html")
            }
        }
    }

    jacoco {
        toolVersion = "0.8.5"
    }

    pitest {
        threads.set(Runtime.getRuntime().availableProcessors())
        testPlugin.set("junit5")
        avoidCallsTo.set(setOf("kotlin.jvm.internal.Intrinsics"))
        outputFormats.set(setOf("HTML"))
        timestampedReports.set(false)
    }
}
