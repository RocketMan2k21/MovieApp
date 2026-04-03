package com.romahduda.movies30.domain.mappers

import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.models.Movie

fun MovieDto.toMovieEntity() : MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        release_date = releaseDate,
        vote_average = voteAverage,
        poster_path = posterPath,
        isFavorite = false
    )
}

fun MovieEntity.toMovie() : Movie =
    Movie(
        id = id,
        title = title,
        release_date = release_date,
        vote_average = vote_average,
        poster_path = poster_path,
        isFavorite = isFavorite
    )