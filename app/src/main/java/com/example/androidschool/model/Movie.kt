package com.example.androidschool.model

class Movie(
    var id: Long,
    var name: String?,
    var year: String?,
    var description: String?,
    val genres: List<Genre>?,
    val countries: List<Country>?,
    var rating: Rating?,
    var poster: Poster?
)

