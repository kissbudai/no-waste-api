import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("plugin.jpa") version "1.2.71"
	id("org.springframework.boot") version "2.1.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
	id("com.google.cloud.tools.jib") version "1.3.0"
}

group = "edu.ubb.micro.no-waste"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly: Configuration by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

repositories {
	mavenCentral()
	jcenter()
}

buildscript {
	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-noarg:1.2.71")
		classpath("org.jetbrains.kotlin:kotlin-allopen:1.2.71")
	}
}

extra["springCloudVersion"] = "Greenwich.SR1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-devtools")
	
	// Eureka
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	
	// Web
	implementation("org.springframework.boot:spring-boot-starter-data-rest")

	// Persistence
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

// Docker configuration
jib {
	from {
		image = "java:8-jre-alpine"
	}
	to {
		image = "com.github.kissbudai/no-waste-product-manager-img"
	}

	container {
		ports = listOf("55000")
	}
}