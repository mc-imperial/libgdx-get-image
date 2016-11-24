#!/bin/bash
set -x
set -e
set -u

./gradlew android:assembleDebug
cd android/build/outputs/apk/
github-release \
  mc-imperial/libgdx-get-image \
  v-${CI_BUILD_REF} \
  ${CI_BUILD_REF} \
  "$(echo -e "Automated build.\n$(git log --graph -n 3 --abbrev-commit --pretty='format:%h - %s <%an>')")" \
  android-debug.apk

