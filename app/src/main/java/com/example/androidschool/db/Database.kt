package com.example.androidschool.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidschool.TypeConverter
import com.example.androidschool.model.entity.MovieEntity

@androidx.room.Database(entities = [MovieEntity::class], version = 6)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun movieDao(): MovieDao

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
                .allowMainThreadQueries()
                .build()
            return INSTANCE as Database
        }
    }
}