import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

allprojects {
	group = "com.sage.everyapart"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

subprojects {

	apply {
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlin.plugin.spring")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		compileOnly("org.projectlombok:lombok")
		developmentOnly("org.springframework.boot:spring-boot-devtools")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.getByName<Test>("test") {
		useJUnitPlatform()
	}

	repositories {
		mavenCentral()
	}

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}
}
