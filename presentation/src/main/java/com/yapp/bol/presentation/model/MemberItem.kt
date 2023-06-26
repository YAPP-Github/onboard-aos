package com.yapp.bol.presentation.model

import android.os.Parcel
import android.os.Parcelable
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING

data class MemberItem(
    val id: Int,
    val name: String,
    val level: Int,
    var isChecked: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: EMPTY_STRING,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(level)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberItem> {
        override fun createFromParcel(parcel: Parcel): MemberItem {
            return MemberItem(parcel)
        }

        override fun newArray(size: Int): Array<MemberItem?> {
            return arrayOfNulls(size)
        }
    }
}
