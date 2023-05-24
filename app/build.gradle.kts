plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.yapp.bgrating.app"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.yapp.bgrating.app"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(com.yapp.bgrating.KTX.CORE)
    implementation(com.yapp.bgrating.AndroidX.APP_COMPAT)
    implementation(com.yapp.bgrating.AndroidX.MATERIAL)
    implementation(com.yapp.bgrating.AndroidX.CONSTRAINT_LAYOUT)
    implementation(com.yapp.bgrating.Test.EXT_JUNIT)
    implementation(com.yapp.bgrating.Test.TEST_RUNNER)
    implementation(com.yapp.bgrating.Test.ESPRESSO_CORE)
//    implementation 'androidx.core:core-ktx:1.8.0'
//    implementation 'androidx.appcompat:appcompat:1.5.1'
//    implementation 'com.google.android.material:material:1.9.0'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'com.android.support.test:runner:1.0.2'
//    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}