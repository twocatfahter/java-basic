plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.study"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val queryDslVersion = "5.1.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4")
	compileOnly("org.projectlombok:lombok")

	runtimeOnly("com.mysql:mysql-connector-j")

	// QueryDSL
	implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	// flyway
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")

//	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val querydslDir = layout.buildDirectory.dir("generated/querydsl").get().asFile

tasks.withType<JavaCompile> {
	options.generatedSourceOutputDirectory.set(querydslDir)
}

sourceSets {
	main {
		java {
			srcDirs(querydslDir)
		}
	}
}

