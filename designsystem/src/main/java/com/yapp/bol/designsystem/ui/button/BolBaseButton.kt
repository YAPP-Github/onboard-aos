package com.yapp.bol.designsystem.ui.button

interface BolBaseButton {
    fun setOnClickListener(onClick: () -> Unit)
    fun disableButton()
    fun enableButton()
}
