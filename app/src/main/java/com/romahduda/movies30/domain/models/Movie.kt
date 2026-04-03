package com.romahduda.movies30.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String?,
    val vote_average: Double,
    val poster_path: String?,
    val isFavorite : Boolean = false
)