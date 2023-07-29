package com.yapp.bol.presentation.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

fun NavController.navigateFragment(@IdRes actionId: Int, vararg bundleData: Pair<String, Any>) {
    val bundle = Bundle()
    bundleData.forEach {
        when (it.second) {
            is String -> bundle.putString(it.first, it.second as String)
            is Int -> bundle.putInt(it.first, it.second as Int)
            is Long -> bundle.putLong(it.first, it.second as Long)
            is Boolean -> bundle.putBoolean(it.first, it.second as Boolean)
            is Parcelable -> bundle.putParcelable(it.first, it.second as Parcelable)
        }
    }

    navigate(actionId, bundle)
}

fun Fragment.backFragment() {
    findNavController().popBackStack()
}
