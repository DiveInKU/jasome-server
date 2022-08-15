#!/usr/bin/env bash

# set -e -o pipefail

PROJECT_ROOT="/home/ubuntu/jasome-server"

echo -e "\
 +------------------------------------+
 |              1. build              |
 +------------------------------------+"

echo -e "\
 +------- git pull... "
cd $PROJECT_ROOT || exit 1
git reset --hard origin/develop
git pull origin develop

echo -e "\
 +------- build... "
./gradlew clean build -x test

