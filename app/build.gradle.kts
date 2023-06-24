plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.yapp.bol.app"
    compileSdk = com.yapp.bol.Applications.compileSdk

    defaultConfig {
        applicationId = "com.yapp.bol.app"
        minSdk = com.yapp.bol.Applications.minSdk
        targetSdk = com.yapp.bol.Applications.targetSdk
        versionCode = com.yapp.bol.Applications.versionCode
        versionName = com.yapp.bol.Applications.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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

    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":presentation")))

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
    implementation(com.yapp.bol.Retrofit.LOGGING_INTERCEPTOR)

    // AndroidX
    implementation(com.yapp.bol.AndroidX.LIFECYCLE_VIEW_MODEL)
    implementation(com.yapp.bol.AndroidX.LIFECYCLE_LIVEDATA)
    implementation(com.yapp.bol.AndroidX.ACTIVITY)
    implementation(com.yapp.bol.AndroidX.FRAGMENT)
    implementation(com.yapp.bol.AndroidX.COMPOSE)

    // Coroutines
    implementation(com.yapp.bol.Coroutines.COROUTINES)

    implementation(com.yapp.bol.DataStore.DATA_STORE_CORE)
    implementation(com.yapp.bol.DataStore.DATA_STORE)
}
