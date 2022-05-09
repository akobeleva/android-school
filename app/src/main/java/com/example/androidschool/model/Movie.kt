package com.example.androidschool.model

data class Movie(
    val id: Long,
    val name: String,
    val year: String,
    val description: String,
    val genres: List<Genre>,
    val countries: List<Country>,
    val rating: Rating,
    val poster: Poster
)
