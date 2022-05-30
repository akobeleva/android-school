package com.example.androidschool.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "scheduled_movies",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        )]
)
data class ScheduledMovieEntity(
    var date: Long,
    var movieId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}