package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class Contest(val name:String, val day: String, val url: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(day)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contest> {
        override fun createFromParcel(parcel: Parcel): Contest {
            return Contest(parcel)
        }

        override fun newArray(size: Int): Array<Contest?> {
            return arrayOfNulls(size)
        }
    }
}
