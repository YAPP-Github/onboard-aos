package com.yapp.bol.app.di

import android.app.Application
import com.yapp.bol.presentation.firebase.FirebaseTracker
import com.yapp.bol.presentation.firebase.Tracer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BOLApplication : Application() {
    @HiltAndroidApp
    class BOLApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            Tracer.tracker = FirebaseTracker(this)
        }
    }
}
