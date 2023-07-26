package com.yapp.bol.domain.model

data class TermsItem(
    val code: String,
    val title: String,
    val url: String,
    val isRequired: Boolean,
    var isChecked: Boolean = false
)
