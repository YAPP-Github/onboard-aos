package com.yapp.bol.domain.model

data class ErrorItem(
    val code: String = "",
    val message: String,
) {
    companion object {
        const val NETWORK_ERROR_CODE = 1002
    }
}
