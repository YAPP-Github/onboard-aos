package com.yapp.bol.presentation.utils

object Converter {
    fun convertLengthToString(maxLength: Int, count: Int): String {
        return "$count/$maxLength"
    }

    fun Double?.convertWinRate(): String {
        return this?.let {
            String.format("%.1f%%", this)
        } ?: kotlin.run { "-" }
    }

    fun Int?.convertPlayCount(): String {
        return this?.let {
            "${this}회"
        } ?: kotlin.run { "-" }
    }
}
