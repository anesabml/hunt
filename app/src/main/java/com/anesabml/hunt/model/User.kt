package com.anesabml.hunt.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: String,
    val coverImage: String?,
    val headline: String?,
    val isMaker: Boolean,
    val isViewer: Boolean,
    val profileImage: String?,
    val username: String,
    val name: String
) : Parcelable {

    constructor(headline: String?, profileImage: String?, name: String) : this(
        "id",
        "coverImage",
        headline,
        false,
        false,
        profileImage,
        "username",
        name
    )

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(coverImage)
        parcel.writeString(headline)
        parcel.writeByte(if (isMaker) 1 else 0)
        parcel.writeByte(if (isViewer) 1 else 0)
        parcel.writeString(profileImage)
        parcel.writeString(username)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
