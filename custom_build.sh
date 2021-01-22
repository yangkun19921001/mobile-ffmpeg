#!/bin/bash

#配置 Android SDK PATH
export ANDROID_HOME=$ANDROID_SDK
#配置 Android  NDK PATH
export ANDROID_NDK_ROOT=/Users/devyk/Data/Android/NDK/android-ndk-r21b

echo $ANDROID_HOME
echo $ANDROID_NDK_ROOT

#        --disable-x86 \
#        --disable-x86 \
#        --disable-arm-v7a-neon \
#        --disable-arm-v7a \
#        --disable-x86-64 \


./android.sh \
        --lts \
        --enable-gpl \
        --disable-x86 \
        --disable-x86 \
        --disable-arm-v7a-neon \
        --disable-x86-64 \
        --enable-android-media-codec \
        --enable-freetype \
        --enable-fontconfig \
        --enable-fribidi \
        --enable-gmp \
        --enable-x264 \
        --enable-gnutls \
        --enable-speex \
        --enable-libwebp \
        --enable-lame \
        --enable-opus \
        --enable-opencore-amr \
