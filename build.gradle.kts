import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	id("java-library")
}

group = "io.github.tsitle"
version = "${project.findProperty("libVersion")}"  // read 'libVersion' from 'gradle.properties'

// ---------------------------------------------------------------------------------------------------------------------

val gitCommitHashProvider = if (!layout.projectDirectory.dir(".git").asFile.isDirectory) {
	providers.provider { "unknown" }
} else {
	providers.exec {
		commandLine("git", "rev-parse", "--verify", "--short", "HEAD")
		isIgnoreExitValue = true
	}.standardOutput.asText.map { output ->
		output.trim().ifEmpty { "unknown" }
	}
}

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

	//
	implementation("org.jspecify:jspecify:1.0.0")
}

// ---------------------------------------------------------------------------------------------------------------------

tasks.compileJava {
	options.javaModuleVersion = provider { version as String }
}
tasks.compileJava.configure {
	options.encoding = "UTF-8"
	options.compilerArgs.add("-Xlint:deprecation")
}

/*
 * see https://docs.gradle.org/current/userguide/java_testing.html
 */
tasks.test {
	useJUnitPlatform()

	maxHeapSize = "1G"

	jvmArgs(
		"--enable-native-access=ALL-UNNAMED",  // for FFmpeg
		"-DlibVersion=${version}"
	)

	jvmArgumentProviders.add(
		CommandLineArgumentProvider {
			listOf("-DgitCommit=${gitCommitHashProvider.get()}")
		}
	)

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

		addTestListener(object : TestListener {
			override fun beforeSuite(suite: TestDescriptor) = Unit

			override fun afterSuite(suite: TestDescriptor, result: TestResult) {
				if (suite.parent == null) {  // will match the outermost suite
					val output = "Results: ${result.resultType} " +
							"(${result.testCount} tests, ${result.successfulTestCount} passed, " +
							"${result.failedTestCount} failed, ${result.skippedTestCount} skipped)"
					val startItem = "|  "
					val endItem = "  |"
					val repeatLength = startItem.length + output.length + endItem.length
					println("\n" + ("-".repeat(repeatLength)) + "\n" + startItem + output + endItem + "\n" + ("-".repeat(repeatLength)))
				}
			}

			override fun beforeTest(testDescriptor: TestDescriptor) = Unit

			override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) = Unit
		})
	}
}

// ---------------------------------------------------------------------------------------------------------------------

val generatedSourcesDir = layout.buildDirectory.dir("generated/sources/libInfo/java")

val generateLibInfo = tasks.register("generateLibInfo") {
	val libVersion = project.version.toString()

	inputs.property("libVersion", libVersion)
	inputs.property("gitCommit", gitCommitHashProvider)
	outputs.dir(generatedSourcesDir)

	doLast {
		val gitCommitHash = gitCommitHashProvider.get()
		val outputDir = generatedSourcesDir.get().asFile.resolve("io/github/tsitle/lib_xrtxp")
		outputDir.mkdirs()

		outputDir.resolve("LibInfo.java").writeText(
			"""
			package io.github.tsitle.lib_xrtxp;

			import java.util.Optional;

			public final class LibInfo {
				private static final String LIB_VERSION = "$libVersion";
				private static final String GIT_COMMIT = "$gitCommitHash";

				public static Optional<String> getLibVersion() {
					if (LIB_VERSION.isBlank() || "null".equals(LIB_VERSION)) { return Optional.empty(); }
					return Optional.of(LIB_VERSION);
				}
				public static Optional<String> getGitCommit() {
					if (GIT_COMMIT.isBlank() || "null".equals(GIT_COMMIT)) { return Optional.empty(); }
					return Optional.of(GIT_COMMIT);
				}
			}
			""".trimIndent()
		)
	}
}

sourceSets {
	main {
		java {
			srcDir(generateLibInfo)
		}
	}
}
