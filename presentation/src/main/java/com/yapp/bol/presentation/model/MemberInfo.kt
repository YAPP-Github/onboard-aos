package com.yapp.bol.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class MemberInfo(
    val id: Int,
    val role: String,
    val nickname: String,
    val level: Int,
    var isChecked: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(role)
        parcel.writeString(nickname)
        parcel.writeInt(level)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberInfo> {
        override fun createFromParcel(parcel: Parcel): MemberInfo {
            return MemberInfo(parcel)
        }

        override fun newArray(size: Int): Array<MemberInfo?> {
            return arrayOfNulls(size)
        }
    }

}
