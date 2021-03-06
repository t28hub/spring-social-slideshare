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

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")
    id("org.sonarqube") version "3.0"
    id("org.jetbrains.dokka") version "0.10.1"
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    // Spring Social
    val springSocialVersion = "2.0.0.BUILD-SNAPSHOT"
    api("org.springframework.social:spring-social-core:$springSocialVersion")
    implementation("org.springframework.social:spring-social-config:$springSocialVersion")
    implementation("org.springframework.social:spring-social-autoconfigure:$springSocialVersion")

    // Jackson
    val jacksonVersion = "2.11.1"
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    // Testing frameworks
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    // Truth
    testImplementation("com.google.truth:truth:1.0.1")
    val autoTruthVersion = "0.0.3-SNAPSHOT"
    testImplementation("io.t28.auto:auto-truth-annotations:$autoTruthVersion")
    kaptTest("io.t28.auto:auto-truth-processor:$autoTruthVersion")

    // Mockito
    testImplementation("org.mockito:mockito-inline:3.4.6")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // Others
    testImplementation("org.pitest:pitest-junit5-plugin:0.12")
}

tasks {
    val jvmTarget = "${JavaVersion.VERSION_1_8}"
    compileKotlin {
        kotlinOptions.jvmTarget = jvmTarget
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = jvmTarget
    }

    test {
        useJUnitPlatform()
    }

    jacocoTestReport {
        reports {
            csv.isEnabled = false
            xml.isEnabled = true
            xml.destination = file("$buildDir/reports/jacoco/jacoco.xml")
            html.isEnabled = true
            html.destination = file("$buildDir/reports/jacoco")
        }
    }

    dokka {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"
        configuration {
            perPackageOption {
                prefix = "io.t28.springframework.social.slideshare.api.impl.xml"
                suppress = true
            }
            perPackageOption {
                prefix = "io.t28.springframework.social.slideshare.connect"
                suppress = true
            }
            perPackageOption {
                prefix = "io.t28.springframework.social.slideshare.ext"
                suppress = true
            }
        }
    }

    create<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("sources")
    }

    create<Jar>("javadocJar") {
        dependsOn(dokka)
        from(dokka)
        archiveClassifier.set("javadoc")
    }
}

sonarqube {
    properties {
        property("sonar.organization", "t28hub")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.projectKey", "t28hub_spring-social-slideshare")
        property("sonar.projectName", "spring-social-slideshare")
        property("sonar.inclusions", "src/main/kotlin/**,src/main/resources/**")
        property("sonar.exclusions", "src/test/kotlin/**,src/test/resources/**")
        property("sonar.kotlin.detekt.reportPaths", "$buildDir/reports/detekt/detekt.xml")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/jacoco.xml")
    }
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            groupId = "${project.group}"
            artifactId = "spring-social-slideshare"
            version = "${project.version}"

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}
