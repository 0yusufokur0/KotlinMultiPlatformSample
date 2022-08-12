package org.jetbrains.chat

import kotlin.js.Date

actual fun timestampMs(): Long {
    return Date.now().toLong()
}
