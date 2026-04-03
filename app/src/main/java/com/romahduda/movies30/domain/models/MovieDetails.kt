package com.romahduda.movies30.domain.models

data class MovieDetails(
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val posterPath: String?,
    val voteAverage: Double,
    val isFavourite : Boolean = false,
    val overview: String,
    val runtime: Int,
    val budget: Int,
    val revenue: Int,
    val tagline: String
)