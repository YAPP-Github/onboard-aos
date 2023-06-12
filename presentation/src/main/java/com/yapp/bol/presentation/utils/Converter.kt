package com.yapp.bol.presentation.utils

object Converter {
    fun convertLengthToString(maxLength: Int, count: Int): String {
        return "$count/$maxLength"
    }
}
