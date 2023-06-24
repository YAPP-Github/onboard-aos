package com.yapp.bol.presentation.model

data class ResultRecordItem(
    val gameName: String,
    val player: List<MemberResultItem>,
    val currentTime: List<String>,
    val resultRecording: () -> Unit,
)
