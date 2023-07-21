package com.yapp.bol.presentation.firebase.analysis

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseTracker(context: Context) : Tracker {

    /**
     * Parameter Mapping
     *
     * category         - event name
     * action           - content type
     * label            - item id
     */

    private val firebaseTracker: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    override fun screen(activity: Activity, screen: String) {
        firebaseTracker.setCurrentScreen(activity, screen, null)
    }

    override fun send(category: String, action: String) {
        with(Bundle()) {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, action)

            firebaseTracker.logEvent(category, this)
        }
    }

    override fun send(category: String, action: String, label: String) {
        with(Bundle()) {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, action)
            putString(FirebaseAnalytics.Param.ITEM_ID, label)

            firebaseTracker.logEvent(category, this)
        }
    }

    override fun send(category: String, action: String, value: Long) {
        with(Bundle()) {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, action)
            putString(FirebaseAnalytics.Param.ITEM_ID, action)
            putString(FirebaseAnalytics.Param.ITEM_VARIANT, value.toString())

            firebaseTracker.logEvent(category, this)
        }
    }

    override fun send(map: Map<String, String>) {
        with(Bundle()) {
            for ((key, value) in map) putString(key, value)

            firebaseTracker.logEvent("action", this)
        }
    }

    override fun timing(category: String, variable: String, label: String, value: Long) {
        with(Bundle()) {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, category)
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, variable)
            putString(FirebaseAnalytics.Param.LEVEL, label)
            putString(FirebaseAnalytics.Param.EXTEND_SESSION, value.toString())
            putString("duration", value.toString())

            firebaseTracker.logEvent("timing", this)
        }
    }
}
