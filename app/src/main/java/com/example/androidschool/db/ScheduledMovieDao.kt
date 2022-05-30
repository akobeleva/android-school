package com.example.androidschool.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.androidschool.model.entity.ScheduledMovieEntity

@Dao
interface ScheduledMovieDao {
    @Insert(onConflict = REPLACE)
    fun insertScheduledMovie(scheduledMovieEntity: ScheduledMovieEntity)

    @Query("SELECT * FROM scheduled_movies WHERE date = :date AND movieId = :movieId")
    fun getScheduledMovie(date: Long, movieId: Long): ScheduledMovieEntity?

//    @Query("SELECT date, name  FROM scheduled_movies JOIN movies ON scheduled_movies.movieId = movies.id")
//    fun getScheduledMovies()
}