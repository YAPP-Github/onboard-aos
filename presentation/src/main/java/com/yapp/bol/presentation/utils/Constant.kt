package com.yapp.bol.presentation.utils

object Constant {
    const val EMPTY_STRING = ""

    // 숫자, 영어, 한글이 1개라도 포함되는지 체크하는 정규식
    const val EMPTY_REGEX = "^(?=.*[\\dA-Za-zㄱ-ㅎㅏ-ㅣ가-힣])[\\dA-Za-zㄱ-ㅎㅏ-ㅣ가-힣 !@#\$%^&()*.?<>|{}+-_~\n]+\$"
    const val NICKNAME_REGEX = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]*"
    const val GAME_RESULT_RECORD = "게임 결과 기록"
    const val RESULT_RECORD_DIALOG_HEIGHT = 462
}
