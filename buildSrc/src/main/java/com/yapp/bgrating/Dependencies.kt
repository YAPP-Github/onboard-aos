package com.yapp.bgrating

import com.yapp.bgrating.Versions.KOTLIN_VERSION
import com.yapp.bgrating.Versions.KTLINT_VERSION

// ktlint-disable filename

object Versions {
    const val KOTLIN_VERSION = "1.6.10"
    const val KTLINT_VERSION = "9.1.0"
}

object Kotlin {
    const val SDK = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
}

object KTX {
    const val CORE = "androidx.core:core-ktx:1.8.0"
}

object AndroidX {
    const val MATERIAL = "com.google.android.material:material:1.9.0"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val APP_COMPAT = "androidx.appcompat:appcompat:1.5.1"
}

object KtLint {
    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:$KTLINT_VERSION"
}

object Test {
    const val EXT_JUNIT = "junit:junit:4.13.2"
    const val TEST_RUNNER = "com.android.support.test:runner:1.0.2"
    const val ESPRESSO_CORE = "com.android.support.test.espresso:espresso-core:3.0.2"
}
