import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
	id("com.google.cloud.tools.jib") version "1.3.0"
}

group = "edu.ubb.micro.nowaste"
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
}

extra["springCloudVersion"] = "Greenwich.SR1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Eureka
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	// Zuul
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-zuul")

	// Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.10.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.10.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.10.5")

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
		image = "com.github.kissbudai/no-waste-proxy-img"
	}

	container {
		ports = listOf("55000")
	}
}