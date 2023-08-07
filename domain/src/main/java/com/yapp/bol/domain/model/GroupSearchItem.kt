package com.yapp.bol.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupSearchItem(
    val hasNext: Boolean,
    val groupItemList: List<GroupItem>,
) : Parcelable

@Parcelize
data class GroupItem(
    val id: Int,
    val name: String,
    val description: String,
    val organization: String,
    val profileImageUrl: String,
    val memberCount: Int,
) : Parcelable
