plugins {
    kotlin("jvm") version "1.3.72"
    idea
    `java-library`
}

group = "io.t28.springframework.social"
version = "1.0.0-SNAPSHOT"
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.social:spring-social-core:1.1.6.RELEASE")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
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
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
