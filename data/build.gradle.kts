plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.yapp.bol.data"
    compileSdk = com.yapp.bol.Applications.compileSdk

    defaultConfig {
        minSdk = com.yapp.bol.Applications.minSdk
        targetSdk = com.yapp.bol.Applications.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = com.yapp.bol.Applications.jvmTarget
    }
}

dependencies {

    implementation(com.yapp.bol.KTX.CORE)
    implementation(com.yapp.bol.AndroidX.APP_COMPAT)
    implementation(com.yapp.bol.AndroidX.MATERIAL)
    implementation(com.yapp.bol.Test.EXT_JUNIT)
    implementation(com.yapp.bol.Test.TEST_RUNNER)
    implementation(com.yapp.bol.Test.ESPRESSO_CORE)

    implementation(project(mapOf("path" to ":domain")))

    // retrofit
    implementation(com.yapp.bol.Retrofit.RETROFIT)
    implementation(com.yapp.bol.Retrofit.CONVERTER_GSON)
    implementation(com.yapp.bol.Retrofit.CONVERTER_JAXB)

    // Hilt
    implementation(com.yapp.bol.DaggerHilt.DAGGER_HILT)
    kapt(com.yapp.bol.DaggerHilt.DAGGER_HILT_COMPILER)
    // implementation(com.yapp.bol.DaggerHilt.DAGGER_HILT_VIEW_MODEL)
    kapt(com.yapp.bol.DaggerHilt.DAGGER_HILT_ANDROIDX_COMPILER)
}
