package com.yapp.bol.presentation.utils

object Constant {
    const val EMPTY_STRING = ""
    // 숫자, 영어, 한글이 1개라도 포함되는지 체크하는 정규식
    const val EMPTY_REGEX = "^(?=.*[\\dA-Za-zㄱ-ㅎㅏ-ㅣ가-힣])[\\dA-Za-zㄱ-ㅎㅏ-ㅣ가-힣 !@#\$%^&()*.?<>|{}+-_~\n]+\$"
}
