# Java Library for RTSP, (S)RTP and (S)RTCP

This library is the basis for the [Java RTSP Server](https://github.com/tsitle/ts_rtsp_server/) project.

## Maven Central Repository

This library is available on the Maven Central Repository.

If you're using Maven as build tool, include it in your `pom.xml` like so:

``` xml
<dependencies>
	<!-- ... -->
	<dependency>
		<groupId>io.github.tsitle</groupId>
		<artifactId>ts_lib_xrtxp</artifactId>
		<version>1.2.2</version>
	</dependency>
</dependencies>
```

Or, if you're using Gradle with the Groovy DSL, add this to your `build.gradle`:

``` groovy
dependencies {
	/* ... */
	implementation 'io.github.tsitle:ts_lib_xrtxp:1.2.2'
}
```

Or, if you're using Gradle with the Kotlin DSL, add this to your `build.gradle.kts`:

``` kotlin
dependencies {
	/* ... */
	implementation("io.github.tsitle:ts_lib_xrtxp:1.2.2")
}
```
