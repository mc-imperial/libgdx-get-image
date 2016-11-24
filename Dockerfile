FROM ubuntu:16.04

RUN \
  apt-get update && \
  apt-get -y install \
    openjdk-8-jdk python maven \
    git golang wget && \
  apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


ENV \
  ANDROID_SDK_FILENAME=android-sdk_r24.4.1-linux.tgz \
  ANDROID_API_LEVELS=android-23 \
  ANDROID_BUILD_TOOLS_VERSION=25.0.0 \
  ANDROID_HOME=/opt/android-sdk-linux

ENV \
  ANDROID_SDK_URL=http://dl.google.com/android/${ANDROID_SDK_FILENAME} \
  PATH=${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools

RUN \
  mkdir -p /opt && \
  cd /opt && \
  wget -q ${ANDROID_SDK_URL} && \
  tar -xzf ${ANDROID_SDK_FILENAME} && \
  rm ${ANDROID_SDK_FILENAME} && \
  echo y | android update sdk --no-ui -a --filter extra-android-m2repository,tools,platform-tools,${ANDROID_API_LEVELS},build-tools-${ANDROID_BUILD_TOOLS_VERSION}

ENV \
  GOPATH=/data/gopath \
  PATH=/data/gopath/bin:${PATH}

COPY . /data/

WORKDIR /data

RUN \
  mkdir -p ${GOPATH} && \
  go get github.com/c4milo/github-release

CMD ["./build_and_release.sh"]

