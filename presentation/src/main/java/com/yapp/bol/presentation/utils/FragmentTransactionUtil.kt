package com.yapp.bol.presentation.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.yapp.bol.presentation.R

fun Fragment.moveFragment(
    fragment: Fragment,
    vararg bundleData: Pair<String, Any>,
) {
    val bundle = Bundle()
    bundleData.forEach {
        when (it.second) {
            is String -> bundle.putString(it.first, it.second as String)
            is Int -> bundle.putInt(it.first, it.second as Int)
            is Boolean -> bundle.putBoolean(it.first, it.second as Boolean)
            is Parcelable -> bundle.putParcelable(it.first, it.second as Parcelable)
        }
    }
    fragment.arguments = bundle

    parentFragmentManager.beginTransaction()
        .replace(R.id.group_container_layout, fragment)
        .addToBackStack(null)
        .commit()
}

fun Fragment.backFragment() {
    parentFragmentManager.popBackStack()
}
