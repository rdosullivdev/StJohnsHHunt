package com.ros.stjohnshhunt.extensions

import android.content.res.AssetManager

fun AssetManager.readAssetsFile(fileName : String): String =
    open(fileName).bufferedReader().use{ it.readText() }