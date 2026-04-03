package com.romahduda.movies30.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("budget") val budget: Int,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("tagline") val tagline: String
)