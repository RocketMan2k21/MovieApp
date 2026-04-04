package com.romahduda.movies30.domain.mappers

import com.romahduda.movies30.data.local.entity.MovieDetailsEntity
import com.romahduda.movies30.data.remote.dto.MovieDetailsDto
import com.romahduda.movies30.domain.models.MovieDetails

fun MovieDetailsDto.toMovieDetailEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        title = title,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
        overview = overview,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        tagline = tagline
    )
}

fun MovieDetailsEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        releaseDate = releaseDate,
        posterPath = posterPath,
        voteAverage = voteAverage,
        overview = overview,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        tagline = tagline
    )
}


