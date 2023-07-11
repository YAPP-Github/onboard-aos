package com.yapp.bol.domain.model

data class ErrorItem(
    val code: String = UNKNOWN_ERROR_CODE.toString(),
    val message: String,
) {
    companion object {
        const val NETWORK_ERROR_CODE = 1002
        const val UNKNOWN_ERROR_CODE = 6008
    }
}
