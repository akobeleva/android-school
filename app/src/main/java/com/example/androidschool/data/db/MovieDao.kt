package com.example.androidschool.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.androidschool.model.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert
    suspend fun insertAll(movies: List<MovieEntity>)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteMovies(): Int

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Long): MovieEntity?

    @Query("SELECT * FROM movies WHERE year = :year")
    suspend fun getMoviesByYear(year: String): List<MovieEntity>

    @Query("UPDATE movies SET isActive = :isActive")
    suspend fun updateNotActiveMovies(isActive: Boolean = false)

    @Query("SELECT * FROM movies WHERE isActive = :isActive")
    suspend fun getActiveMovies(isActive: Boolean = true): List<MovieEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
}