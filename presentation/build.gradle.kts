import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.yapp.bol.presentation"
    compileSdk = com.yapp.bol.Applications.compileSdk

    defaultConfig {
        minSdk = com.yapp.bol.Applications.minSdk
        targetSdk = com.yapp.bol.Applications.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "KAKAO_API_KEY", getProperty("KAKAO_API_KEY"))
        buildConfigField("String", "GOOGLE_LOGIN_API_KEY", getProperty("GOOGLE_LOGIN_API_KEY"))
        manifestPlaceholders["kakaoKay"] = getProperty("KAKAO_API_KEY_MANI")
        buildConfigField("String", "NAVER_CLIENT_ID", getProperty("NAVER_CLIENT_ID"))
        buildConfigField("String", "NAVER_CLIENT_NAME", getProperty("NAVER_CLIENT_NAME"))
        buildConfigField("String", "NAVER_CLIENT_SECRET", getProperty("NAVER_CLIENT_SECRET"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = com.yapp.bol.Applications.sourceCompatibilityVersion
        targetCompatibility = com.yapp.bol.Applications.targetCompatibilityVersion
    }

    kotlinOptions {
        jvmTarget = com.yapp.bol.Applications.jvmTarget
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))

    implementation(com.yapp.bol.KTX.CORE)
    implementation(com.yapp.bol.AndroidX.APP_COMPAT)
    implementation(com.yapp.bol.AndroidX.MATERIAL)
    implementation(com.yapp.bol.AndroidX.CONSTRAINT_LAYOUT)
    implementation(com.yapp.bol.Test.JUNIT)
    implementation(com.yapp.bol.Test.TEST_RUNNER)
    implementation(com.yapp.bol.Test.ESPRESSO_CORE)

    // Hilt
    implementation(com.yapp.bol.DaggerHilt.DAGGER_HILT)
    kapt(com.yapp.bol.DaggerHilt.DAGGER_HILT_COMPILER)
    kapt(com.yapp.bol.DaggerHilt.DAGGER_HILT_ANDROIDX_COMPILER)

    // retrofit
    implementation(com.yapp.bol.Retrofit.RETROFIT)
    implementation(com.yapp.bol.Retrofit.CONVERTER_GSON)
    implementation(com.yapp.bol.Retrofit.CONVERTER_JAXB)

    // AndroidX
    implementation(com.yapp.bol.AndroidX.LIFECYCLE_VIEW_MODEL)
    implementation(com.yapp.bol.AndroidX.LIFECYCLE_LIVEDATA)
    implementation(com.yapp.bol.AndroidX.ACTIVITY)
    implementation(com.yapp.bol.AndroidX.FRAGMENT)
    implementation(com.yapp.bol.AndroidX.COMPOSE)

    // Coroutines
    implementation(com.yapp.bol.Coroutines.COROUTINES)

    // OAuth
    implementation(com.yapp.bol.OAuth.NAVER)
    implementation(com.yapp.bol.OAuth.KAKAO)
    implementation(com.yapp.bol.Firebase.GMS_AUTH)

    // Paging3
    implementation(com.yapp.bol.AndroidX.PAGING)

    // Glide
    implementation(com.yapp.bol.Glide.GLIDE)
    kapt(com.yapp.bol.Glide.GLIDE_COMPILER)
}

fun getProperty(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}
