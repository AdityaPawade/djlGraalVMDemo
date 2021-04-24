#!/bin/bash -x

LOCAL_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
MOUNT_DIR='/mnt/demo'
M2_DIR="${HOME}"/.m2

function env_build() {

  docker build -t adityap174/poc:buster build/env/buster
  docker build -t adityap174/poc:buster-graalvm build/env/buster-graalvm
}

function jar_build() {

  docker run \
    -it \
    -m 13g \
    --cpus=1 \
    -e MOUNT_DIR="${MOUNT_DIR}" \
    -v "${LOCAL_DIR}":"${MOUNT_DIR}" \
    -v "${M2_DIR}":"${MOUNT_DIR}"/.m2 \
    adityap174/poc:buster-graalvm $MOUNT_DIR/native/build/buildDemoJar.sh
}

function native_build() {

  docker run \
    -it \
    -m 13g \
    --cpus=1 \
    -e MOUNT_DIR="${MOUNT_DIR}" \
    -v "${LOCAL_DIR}":"${MOUNT_DIR}" \
    -v "${M2_DIR}":"${MOUNT_DIR}"/.m2 \
    adityap174/poc:buster-graalvm $MOUNT_DIR/native/build/buildDemoNative.sh
}

function launch_bash_on_build_env() {

  docker run \
    -it \
    -m 13g \
    --cpus=1 \
    -e MOUNT_DIR="${MOUNT_DIR}" \
    -v "${LOCAL_DIR}":"${MOUNT_DIR}" \
    -v "${M2_DIR}":"${MOUNT_DIR}"/.m2 \
    -p 17402:17402 \
    adityap174/poc:buster-graalvm bash
}

function launch_bash_on_test_env() {

  docker run \
    -it \
    -m 13g \
    --cpus=1 \
    -e MOUNT_DIR="${MOUNT_DIR}" \
    -v "${LOCAL_DIR}":"${MOUNT_DIR}" \
    -v "${M2_DIR}":"${MOUNT_DIR}"/.m2 \
    -p 17402:17402 \
    adityap174/poc:buster bash
}

function jar_run() {

	docker run \
    -it \
    -m 13g \
    --cpus=1 \
    -e MOUNT_DIR="${MOUNT_DIR}" \
    -v "${LOCAL_DIR}":"${MOUNT_DIR}" \
    -v "${M2_DIR}":"${MOUNT_DIR}"/.m2 \
    -p 17402:17402 \
    adityap174/poc:buster-graalvm \
    java -agentlib:native-image-agent=config-output-dir=$MOUNT_DIR/native/configs/META-INF/native-image/ \
    -cp $MOUNT_DIR/target/demo-1.0-bin.jar com.jp.demo.DJLGraalVMDemo
}

function native_run() {

	docker run \
    -it \
    -m 13g \
    --cpus=1 \
    -e MOUNT_DIR="${MOUNT_DIR}" \
    -v "${LOCAL_DIR}":"${MOUNT_DIR}" \
    -p 17402:17402 \
    adityap174/poc:buster $MOUNT_DIR/native/build/runDemoNative.sh
}

action=$1
case "$action" in

	env)
    env_build
		;;
	package)
    jar_build
		;;
	build)
    native_build
		;;
	build_bash)
    launch_bash_on_build_env
    ;;
  test_bash)
    launch_bash_on_test_env
    ;;
	run)
    jar_run
    ;;
	test)
    native_run
    ;;
	*)
    echo "Usage: $0 {build_bash|test_bash|env|package|build|run|test}" >&2
    exit 1
    ;;
esac
