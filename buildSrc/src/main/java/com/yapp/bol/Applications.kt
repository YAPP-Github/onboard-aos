package com.yapp.bol

import org.gradle.api.JavaVersion

object Applications {
    // APP Config
    const val minSdk = 21
    const val targetSdk = 32
    const val compileSdk = 32
    const val jvmTarget = "1.8"
    const val versionCode = 2
    const val majorVersion = 1
    const val minorVersion = 0
    const val patchVersion = 1
    const val versionName = "$majorVersion.$minorVersion$patchVersion"
    val sourceCompatibilityVersion = JavaVersion.VERSION_1_8
    val targetCompatibilityVersion = JavaVersion.VERSION_1_8
}
