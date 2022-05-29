package com.example.androidschool.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.androidschool.model.entity.MovieEntity
import com.example.androidschool.model.entity.ScheduledMovieEntity

@Dao
interface MovieDao {
    @Insert
    fun insertAll(movies: List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("DELETE FROM movies")
    fun deleteMovies(): Int

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>?

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Long): MovieEntity?

    @Query("SELECT * FROM movies WHERE year = :year")
    fun getMoviesByYear(year: String): List<MovieEntity>

    @Query("UPDATE movies SET isActive = :isActive")
    fun updateNotActiveMovies(isActive: Boolean = false)

    @Query("SELECT * FROM movies WHERE isActive = :isActive")
    fun getActiveMovies(isActive: Boolean = true): List<MovieEntity>

    @Insert
    fun insertMovie(movie: MovieEntity)

    @Insert
    fun insertScheduledMovie(scheduledMovieEntity: ScheduledMovieEntity)

    @Query("SELECT * FROM scheduled_movies WHERE date = :date AND movieId = :movieId")
    fun getScheduledMovie(date: Long, movieId: Long): ScheduledMovieEntity?

    @Query("SELECT date name  FROM scheduled_movies JOIN movies ON scheduled_movies.movieId = movies.id")
    fun getScheduledMovies()
}