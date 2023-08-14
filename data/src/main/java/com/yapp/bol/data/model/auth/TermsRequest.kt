package com.yapp.bol.data.model.auth

data class TermsRequest(
    val agree: List<String>,
    val disagree: List<String>?,
)
