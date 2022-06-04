package com.example.androidschool.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.androidschool.model.entity.MovieEntity
import com.example.androidschool.model.entity.ScheduledMovieEntity

@Dao
interface ScheduledMovieDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertScheduledMovie(scheduledMovieEntity: ScheduledMovieEntity)

    @Query("SELECT * FROM scheduled_movies JOIN movies ON scheduled_movies.movieId = movies.id")
    suspend fun getScheduledMovies(): Map<ScheduledMovieEntity, List<MovieEntity>>
}