package com.romahduda.movies30.domain.mappers

import com.romahduda.movies30.data.remote.dto.MovieDetailsDto
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        release_date = releaseDate,
        vote_average = voteAverage,
        poster_path = posterPath
    )
}

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
        tagline = tagline
    )
}
