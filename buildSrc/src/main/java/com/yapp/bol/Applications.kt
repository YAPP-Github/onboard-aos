package com.yapp.bol

import org.gradle.api.JavaVersion

object Applications {
    // APP Config
    const val minSdk = 21
    const val targetSdk = 33
    const val compileSdk = 33
    const val jvmTarget = "1.8"
    const val versionCode = 3
    const val majorVersion = 1
    const val minorVersion = 1
    const val patchVersion = 0
    const val versionName = "$majorVersion.$minorVersion$patchVersion"
    val sourceCompatibilityVersion = JavaVersion.VERSION_1_8
    val targetCompatibilityVersion = JavaVersion.VERSION_1_8
}
