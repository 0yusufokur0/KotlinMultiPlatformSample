#!/bin/bash

kill $(ps aux | grep -i 'Main_desktopKt' | awk '{print $2}') &
adb shell pm uninstall --user 0 org.jetbrains.chat  &
xcrun simctl uninstall booted org.jetbrains.chat.Chat
