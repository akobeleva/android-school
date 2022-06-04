package com.example.androidschool.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidschool.TypeConverter
import com.example.androidschool.model.entity.MovieEntity
import com.example.androidschool.model.entity.ScheduledMovieEntity

@androidx.room.Database(entities = [MovieEntity::class, ScheduledMovieEntity::class], version = 8)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun scheduledMovieDao(): ScheduledMovieDao

    companion object {
        private var INSTANCE: Database? = null
        fun getDatabase(context: Context): Database {
            if (INSTANCE != null) return INSTANCE as Database
            INSTANCE = Room.databaseBuilder(
                context,
                Database::class.java,
                "movie_database"
            )
                .addTypeConverter(TypeConverter())
                .fallbackToDestructiveMigration()
                .build()
            return INSTANCE as Database
        }
    }
}