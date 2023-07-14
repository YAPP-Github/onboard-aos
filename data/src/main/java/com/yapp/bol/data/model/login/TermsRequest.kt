package com.yapp.bol.data.model.login

data class TermsRequest(
    val agree : List<String>,
    val disagree: List<String>?,
)
