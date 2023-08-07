package com.yapp.bol.presentation.view.group.join.type

sealed class GroupResultType {
    data class LOADING(val message: String = "모임에 들어가는 중") : GroupResultType()
    object SUCCESS : GroupResultType()
    data class ValidationNickname(val message: String = "이미 있는 이름입니다. 다른 이름을 설정해주세요.") : GroupResultType()
    data class ValidationAccessCode(val message: String = "참여 코드가 맞지 않습니다.") : GroupResultType()
    data class UnknownError(val message: String) : GroupResultType()
}
