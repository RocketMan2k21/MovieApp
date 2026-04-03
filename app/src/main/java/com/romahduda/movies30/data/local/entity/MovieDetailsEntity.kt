package com.romahduda.movies30.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailsEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("releaseDate")val releaseDate: String?,
    @ColumnInfo("posterPath")val posterPath: String?,
    @ColumnInfo("voteAverage")val voteAverage: Double,
    @ColumnInfo("isFavorite")val isFavorite : Boolean = false,
    @ColumnInfo("overview")val overview: String,
    @ColumnInfo("runtime")val runtime: Int,
    @ColumnInfo("budget") val budget: Int,
    @ColumnInfo("revenue") val revenue: Int,
    @ColumnInfo("tagline") val tagline: String
)
