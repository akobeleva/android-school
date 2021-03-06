package com.example.androidschool.model

import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.Poster
import com.example.androidschool.model.dto.Rating
import com.example.androidschool.model.dto.ScheduledMovie
import com.example.androidschool.model.entity.MovieEntity
import com.example.androidschool.model.entity.ScheduledMovieEntity

class MovieConverter {

    fun movieToEntity(movie: Movie) = movieToEntity(movie, false)

    fun movieToEntity(movie: Movie, isActive: Boolean): MovieEntity = MovieEntity(
        movie.id,
        movie.name,
        movie.year,
        movie.description,
        movie.genres,
        movie.countries,
        movie.rating?.kp,
        movie.poster?.url,
        isActive
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

    fun entityToScheduledMovie(entity: ScheduledMovieEntity): ScheduledMovie = ScheduledMovie(
        entity.id,
        entity.date,
        entity.movieId,
    )
}