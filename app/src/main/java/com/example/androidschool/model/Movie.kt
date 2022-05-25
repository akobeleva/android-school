package com.example.androidschool.model

import android.os.Parcel
import android.os.Parcelable

class Movie(
    var id: Long,
    var name: String?,
    var year: String?,
    var description: String?,
    val genres: List<Genre>?,
    val countries: List<Country>?,
    var rating: Rating?,
    var poster: Poster?
) : Parcelable {


    private constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        name = parcel.readString(),
        year = parcel.readString(),
        description = parcel.readString(),
        null,
        null,
        rating = parcel.readSerializable() as Rating?,
        poster = parcel.readSerializable() as Poster?
    ) {
        parcel.readList(genres!!, Genre::class.java.classLoader)
        parcel.readList(countries!!, Country::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(year)
        parcel.writeString(description)
        parcel.writeTypedList(genres)
        parcel.writeTypedList(countries)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
