package com.yapp.bol.presentation.view.group.join.type

import androidx.annotation.StringRes
import com.yapp.bol.presentation.R

sealed class GroupResultType {
    data class LOADING(
        @StringRes val message: Int = R.string.group_join_entering,
    ) : GroupResultType()

    object SUCCESS : GroupResultType()
    data class ValidationNickname(
        @StringRes val message: Int = R.string.group_join_already_exists_nickname,
    ) : GroupResultType()

    data class ValidationAccessCode(
        @StringRes val message: Int = R.string.group_join_not_correct_code,
    ) : GroupResultType()

    data class UnknownError(val message: String) : GroupResultType()
}
