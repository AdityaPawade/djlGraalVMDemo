#!/bin/bash

LOCAL_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
echo "local dir : $LOCAL_DIR"
ROOT_DIR="$LOCAL_DIR"/../..
echo "root dir : $ROOT_DIR"

function mvn-there() {
  DIR="$1"
  shift
  (cd "$DIR" || exit; mvn "$@")
}

mvn-there "$ROOT_DIR" -Plinux clean package -Dmaven.home=$MOUNT_DIR/.m2 -Dmaven.repo.local=$MOUNT_DIR/.m2/repository