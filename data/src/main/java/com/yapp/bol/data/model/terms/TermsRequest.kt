package com.yapp.bol.data.model.terms

data class TermsRequest(
    val agree: List<String>,
    val disagree: List<String>?,
)
