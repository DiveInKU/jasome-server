#!/usr/bin/env bash

# set -e -o pipefail

PROJECT_ROOT="/home/ubuntu/jasome-server"

echo -e "\
 +------------------------------------+
 |              1. build              |
 +------------------------------------+"

cd $PROJECT_ROOT || exit 1
./gradlew clean build


