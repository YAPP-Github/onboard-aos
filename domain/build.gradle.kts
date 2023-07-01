plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.yapp.bol.domain"
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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = com.yapp.bol.Applications.sourceCompatibilityVersion
        targetCompatibility = com.yapp.bol.Applications.targetCompatibilityVersion
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

    // retrofit
    implementation(com.yapp.bol.Retrofit.RETROFIT)
    implementation(com.yapp.bol.Retrofit.CONVERTER_GSON)
    implementation(com.yapp.bol.Retrofit.CONVERTER_JAXB)

    // Hilt
    implementation(com.yapp.bol.DaggerHilt.DAGGER_HILT)
    kapt(com.yapp.bol.DaggerHilt.DAGGER_HILT_COMPILER)
    kapt(com.yapp.bol.DaggerHilt.DAGGER_HILT_ANDROIDX_COMPILER)

    // Paging 아래 paging library는 android dependency가 없습니다.
    implementation(com.yapp.bol.AndroidX.PAGING_WITHOUT_ANDROID)
}
