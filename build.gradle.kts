subprojects {
    apply(plugin = "java")

    group = "io.t28.springframework.social"
    version = "1.0.0-SNAPSHOT"
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenCentral()
        jcenter()
    }
}
