package com.yapp.bol.presentation.utils

import com.yapp.bol.presentation.utils.Constant.EMPTY_REGEX

fun String?.checkedEmptyValue(): Boolean {
    if(this == null) return false
    val regex = Regex(EMPTY_REGEX)
    return this.matches(regex)
}
