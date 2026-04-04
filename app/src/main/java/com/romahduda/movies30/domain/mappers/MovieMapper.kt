package com.romahduda.movies30.domain.mappers

import com.romahduda.movies30.data.remote.dto.MovieDetailsDto
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import com.romahduda.movies30.domain.models.MovieToUpdate

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
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
        tagline = tagline,
    )
}

fun MovieDetails.toMovieUpdate(): MovieToUpdate =
    MovieToUpdate(
        id = id,
        title = title,
        isFavorite = isFavourite
    )

