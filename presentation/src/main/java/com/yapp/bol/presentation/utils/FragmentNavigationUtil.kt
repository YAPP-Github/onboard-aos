package com.yapp.bol.presentation.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.navigateFragment(@IdRes actionId: Int, vararg bundleData: Pair<String, Any>) {
    val bundle = Bundle()
    bundleData.forEach {
        bundle.putParcelable(it.first, it.second as Parcelable)
    }

    navigate(actionId, bundle)
}
