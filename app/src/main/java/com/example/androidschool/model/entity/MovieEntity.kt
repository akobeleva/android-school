package com.example.androidschool.model.entity

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschool.model.dto.Country
import com.example.androidschool.model.dto.Genre

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val name: String?,
    val year: String?,
    val description: String?,
    @ColumnInfo(typeAffinity = TEXT) val genres: List<Genre>?,
    @ColumnInfo(typeAffinity = TEXT) val countries: List<Country>?,
    val rating: Float?,
    val poster: String?,
    val isActive: Boolean
)
