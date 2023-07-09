package com.yapp.bol.presentation.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.yapp.bol.presentation.R

fun Fragment.moveFragment(fragment: Fragment, vararg bundleData: Pair<String, Any>) {
    val bundle = Bundle()
    bundleData.forEach {
        bundle.putParcelable(it.first, it.second as Parcelable)
    }
    fragment.arguments = bundle

    parentFragmentManager.beginTransaction()
        .replace(R.id.group_container_layout, fragment)
        .commit()
}
