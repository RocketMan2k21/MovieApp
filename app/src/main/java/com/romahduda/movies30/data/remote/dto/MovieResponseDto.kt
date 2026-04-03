package com.romahduda.movies30.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: List<MovieDto> = listOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)