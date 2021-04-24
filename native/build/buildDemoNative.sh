#!/bin/bash

LOCAL_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
echo "local dir : $LOCAL_DIR"
ROOT_DIR="$LOCAL_DIR"/../..
echo "root dir : $ROOT_DIR"
META_DIR="$ROOT_DIR"/native/configs
echo "meta dir : $META_DIR"
#TARGET="$ROOT_DIR"/dist/server/bin/"$1"/gameServer
TARGET="$ROOT_DIR"/native/bin/demo
echo "target dir : $TARGET"
JAR_DIR="$ROOT_DIR"/target/demo-1.0-bin.jar
echo "jar dir : $JAR_DIR"

#  -J-Xmx2G \
#	--report-unsupported-elements-at-runtime \

native-image \
  -Dorg.bytedeco.javacpp.logger=slf4j \
  -H:-DeadlockWatchdogExitOnTimeout \
  --no-server \
  --static \
  --libc=glibc \
  --verbose \
	-cp "$META_DIR":"$JAR_DIR" com.jp.demo.DJLGraalVMDemo \
	-H:Name="$TARGET" \
	-H:EnableURLProtocols=http,https \
	-H:+ReportExceptionStackTraces \
	--initialize-at-build-time=org.eclipse.jetty,javax.servlet,org.slf4j,org.apache.logging,ch.qos.logback \
	--no-fallback \
	--allow-incomplete-classpath \
	--features=org.graalvm.home.HomeFinderFeature \
	--enable-all-security-services \
  -H:+AddAllCharsets \
  --install-exit-handlers \
  | grep -v ClassNotFoundException
	
#upx -9 "$TARGET"