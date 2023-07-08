package com.yapp.bol.presentation.model

data class ResultRecordItem( // todo 이름이 명확하지 않음,
    val gameName: String,
    val player: List<MemberResultItem>,
    val currentTime: List<String>,
    val resultRecording: () -> Unit,
)
