package com.romahduda.movies30.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("liked_movies_table")
data class LikedMovieEntity(
    @PrimaryKey
    @ColumnInfo("movie_id") val id : Int
)
