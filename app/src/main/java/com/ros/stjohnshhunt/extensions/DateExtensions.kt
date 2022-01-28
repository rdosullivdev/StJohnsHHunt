package com.ros.stjohnshhunt.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toReadableString(format: String): String {
    return SimpleDateFormat(format).format(this)
}

