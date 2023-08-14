package com.yapp.bol.data.model.auth

data class TermsDTO(
    val code: String,
    val title: String,
    val url: String,
    val isRequired: Boolean,
)
