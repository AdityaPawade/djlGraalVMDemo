#!/bin/bash

LOCAL_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
echo "local dir : $LOCAL_DIR"
ROOT_DIR="$LOCAL_DIR"/../..
echo "root dir : $ROOT_DIR"
BINARY="$ROOT_DIR"/native/bin/demo
echo "binary dir : $BINARY"

$BINARY
