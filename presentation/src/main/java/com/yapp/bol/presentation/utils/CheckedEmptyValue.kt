package com.yapp.bol.presentation.utils

import com.yapp.bol.presentation.utils.Constant.EMPTY_REGEX

val isEmptyValue: (String?) -> Boolean = { value ->
    val regex = Regex(EMPTY_REGEX)
     value?.matches(regex) ?: false
}
