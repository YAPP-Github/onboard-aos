package com.yapp.bol.presentation.firebase.analysis

import android.app.Activity

interface Tracker {
    fun screen(activity: Activity, screen: String)
    fun send(category: String, action: String)
    fun send(category: String, action: String, label: String)
    fun send(category: String, action: String, value: Long)
    fun send(map: Map<String, String>)
    fun timing(category: String, variable: String, label: String, value: Long)
}
