#!/bin/bash

native-image \
	-Dconfig=configurations/local \
	-cp native/demo:target/demo-1.0-bin.jar com.jp.demo.DJLGraalVMDemo \
	-H:Name=target/demo \
	-H:EnableURLProtocols=http,https \
	-H:+ReportExceptionStackTraces \
	--initialize-at-build-time=org.slf4j,org.apache.logging,ch.qos.logback \
	--initialize-at-run-time=ai.djl,org.bytedeco \
	--no-fallback \
	--allow-incomplete-classpath \
	--features=org.graalvm.home.HomeFinderFeature \
	--report-unsupported-elements-at-runtime \
	--enable-all-security-services
	#--trace-class-initialization=ai.djl.engine.Engine