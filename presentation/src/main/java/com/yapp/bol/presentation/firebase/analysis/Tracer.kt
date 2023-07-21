package com.yapp.bol.presentation.firebase.analysis

import android.app.Activity

object Tracer {
    private var sTracker: Tracker? = null

    var tracker: Tracker?
        get() = sTracker
        set(tracker) {
            sTracker = tracker
        }

    fun screen(activity: Activity, screen: String) {
        sTracker?.screen(activity, screen)
    }

    fun send(category: String, action: String) {
        sTracker?.send(category, action)
    }

    fun send(category: String, action: String, label: String) {
        sTracker?.send(category, action, label)
    }

    fun send(category: String, action: String, value: Long) {
        sTracker?.send(category, action, value)
    }

    fun timing(category: String, variable: String, label: String, value: Long) {
        sTracker?.timing(category, variable, label, value)
    }
}
