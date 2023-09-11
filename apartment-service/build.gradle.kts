
plugins {
	kotlin("plugin.jpa") version "1.8.22"

    //docker plugin
    id("com.google.cloud.tools.jib") version "3.1.4"
}

group = "com.sage.everyapart"
version = "0.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //db driver
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    //xml 파싱
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2")

    // test
    // https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5
    testImplementation("io.kotest:kotest-runner-junit5:5.7.1")
    // https://mvnrepository.com/artifact/io.kotest.extensions/kotest-extensions-spring
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    //swagger
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

}

jib {

    from {
        image = "openjdk:17-jdk-slim"
        platforms {
            platform {
                architecture = "arm64"
                os = "linux"
            }
        }
    }

    to {
        image = "${rootProject.name}-${project.name}"
        tags = setOf("$version")
    }

    container{
        jvmFlags = listOf("-Xms512m", "-Xmx512m")
    }
}



