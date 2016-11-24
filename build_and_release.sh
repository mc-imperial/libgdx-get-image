#!/bin/bash
set -x
set -e
set -u

rm -rf build_release || true
mkdir build_release
./gradlew desktop:dist
cp desktop/build/libs/desktop-1.0.jar build_release/libgdx-get-image-desktop.jar
./gradlew android:assembleDebug
cp android/build/outputs/apk/android-debug.apk build_release/libgdx-get-image-android-debug.apk
github-release \
  mc-imperial/libgdx-get-image \
  v-${CI_BUILD_REF} \
  ${CI_BUILD_REF} \
  "$(echo -e "Automated build.\n$(git log --graph -n 3 --abbrev-commit --pretty='format:%h - %s <%an>')")" \
  'build_release/*'

