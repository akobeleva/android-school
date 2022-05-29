package com.example.androidschool

import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.Poster
import com.example.androidschool.model.dto.Rating
import com.example.androidschool.model.entity.MovieEntity

class MovieConverter {
    fun movieToEntity(movie: Movie): MovieEntity = MovieEntity(
        movie.id,
        movie.name,
        movie.year,
        movie.description,
        movie.genres,
        movie.countries,
        movie.rating?.kp,
        movie.poster?.url,
        true
    )

    fun entityToMovie(entity: MovieEntity): Movie = Movie(
        entity.id,
        entity.name,
        entity.year,
        entity.description,
        entity.genres,
        entity.countries,
        entity.rating?.let { Rating(it) },
        entity.poster?.let { Poster(it) }
    )
}