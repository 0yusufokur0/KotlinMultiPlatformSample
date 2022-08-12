package org.jetbrains.chat

actual fun timestampMs(): Long {
    return System.currentTimeMillis()
}
