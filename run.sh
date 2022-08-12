#!/bin/bash
./gradlew installDebug &&
adb uninstall org.jetbrains.chat &&
adb install build/outputs/apk/debug/chat-mpp-debug.apk &&
adb shell am start -n org.jetbrains.chat/.MainActivity &
./gradlew run  &
./gradlew iosDeployIPhone8Debug &
./gradlew jsBrowserDevelopmentRun