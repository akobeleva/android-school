package com.example.androidschool.model

import android.os.Parcel
import android.os.Parcelable

data class Country(
    val name: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}