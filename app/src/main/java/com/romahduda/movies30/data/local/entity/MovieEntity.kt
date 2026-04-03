package com.romahduda.movies30.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("release_date") val release_date: String?,
    @ColumnInfo("vote_average") val vote_average: Double,
    @ColumnInfo("poster_path") val poster_path: String?,
    @ColumnInfo("isFavorite") val isFavorite : Boolean = false,
    val remoteIndex : Int
)
