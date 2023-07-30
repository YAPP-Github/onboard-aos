package com.yapp.bol.presentation.utils

import com.yapp.bol.presentation.utils.Constant.EMPTY_REGEX
import com.yapp.bol.presentation.utils.Constant.NICKNAME_REGEX

val isInputTextValid: (String?) -> Boolean = { value ->
    val regex = Regex(EMPTY_REGEX)
    value?.matches(regex) ?: false
}

val isNicknameValid: (String?) -> Boolean = { value ->
    val regex = Regex(NICKNAME_REGEX)
    if (value?.isEmpty() == true) true
    else value?.matches(regex) ?: false

}
