
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

    //jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    //vault
    implementation("org.springframework.vault:spring-vault-core:2.3.3")

    // test
    // https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5
    testImplementation("io.kotest:kotest-runner-junit5:5.7.1")
    // https://mvnrepository.com/artifact/io.kotest.extensions/kotest-extensions-spring
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("io.kotest:kotest-assertions-core:5.4.2") // shouldBe... etc 와같이 Assertions 의 기능을 제공


    //swagger
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    //password encoder
    implementation("org.springframework.security:spring-security-crypto:5.7.1")

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



