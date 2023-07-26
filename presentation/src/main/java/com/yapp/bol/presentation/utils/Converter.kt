package com.yapp.bol.presentation.utils

object Converter {
    fun convertLengthToString(maxLength: Int, count: Int): String {
        return "$count/$maxLength"
    }

    fun Int?.convertScore(): String {
        return this?.toString() ?: kotlin.run { "-" }
    }

    fun Int?.convertPlayCount(): String {
        return this?.let {
            "${it}회"
        } ?: kotlin.run { "-" }
    }
}
