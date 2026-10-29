import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	id("java-library")
}

group = "org.tsitle"
version = "${project.findProperty("libVersion")}"  // read 'libVersion' from 'gradle.properties'

// ---------------------------------------------------------------------------------------------------------------------

java {
	/*
	 * Show auto-detectable JDKs:
	 *   $ ./gradlew -q javaToolchains
	 */

	sourceCompatibility = JavaVersion.VERSION_25
	/*toolchain {
		languageVersion.set(JavaLanguageVersion.of(25))
	}*/
}

/*
 * see https://docs.gradle.org/current/userguide/java_testing.html
 */
tasks.test {
	useJUnitPlatform()

	/*
	 * testLogging based on
	 *   https://stackoverflow.com/a/36130467
	 */
	testLogging {
		// set options for log level LIFECYCLE
		events(
			TestLogEvent.FAILED,
			TestLogEvent.PASSED,
			TestLogEvent.SKIPPED,
			TestLogEvent.STANDARD_OUT
		)
		exceptionFormat = TestExceptionFormat.FULL
		showExceptions = true
		showCauses = true
		showStackTraces = true

		// set options for log level DEBUG and INFO
		debug {
			events(
				TestLogEvent.STARTED,
				TestLogEvent.FAILED,
				TestLogEvent.PASSED,
				TestLogEvent.SKIPPED,
				TestLogEvent.STANDARD_ERROR,
				TestLogEvent.STANDARD_OUT
			)
			exceptionFormat = TestExceptionFormat.FULL
		}
		info.events = debug.events
		info.exceptionFormat = debug.exceptionFormat

		afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
			if (desc.parent == null) {  // will match the outermost suite
				val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)"
				val startItem = "|  "
				val endItem = "  |"
				val repeatLength = startItem.length + output.length + endItem.length
				println("\n" + ("-".repeat(repeatLength)) + "\n" + startItem + output + endItem + "\n" + ("-".repeat(repeatLength)))
			}
		}))
	}
}

tasks.compileJava {
	options.javaModuleVersion = provider { version as String }
}
tasks.compileJava.configure {
	options.encoding = "UTF-8"
	options.compilerArgs.add("-Xlint:deprecation")
}

// ---------------------------------------------------------------------------------------------------------------------

repositories {
	mavenCentral()
}

dependencies {
	// for Unit Tests
	val depsVersionOrgJunitJunitBom = project.findProperty("depsVersionOrgJunitJunitBom")!! as String
	testImplementation(platform("org.junit:junit-bom:${depsVersionOrgJunitJunitBom}"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	//testRuntimeOnly("org.junit.vintage:junit-vintage-engine")

	//
	implementation("org.jspecify:jspecify:1.0.0")
}

/*
 * see https://docs.gradle.org/current/userguide/java_testing.html
 */
tasks.test {
	useJUnitPlatform()

	maxHeapSize = "1G"

	testLogging {
		events("passed")
	}
}
