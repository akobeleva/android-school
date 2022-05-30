package com.example.androidschool.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.androidschool.model.entity.MovieEntity

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

    @Insert(onConflict = REPLACE)
    fun insertMovie(movie: MovieEntity)
}