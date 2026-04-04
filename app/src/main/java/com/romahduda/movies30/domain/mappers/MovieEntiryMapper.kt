package com.romahduda.movies30.domain.mappers

import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.models.Movie

fun MovieDto.toMovieEntity(remoteIndex: Int): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        release_date = this.releaseDate,
        vote_average = this.voteAverage,
        poster_path = this.posterPath,
        remoteIndex = remoteIndex
    )
}

fun MovieEntity.toMovie(): Movie =
    Movie(
        id = id,
        title = title,
        release_date = release_date,
        vote_average = vote_average,
        poster_path = poster_path,
        isFavorite = isFavorite,
        remoteIndex = remoteIndex
    )

fun Movie.toMovieEntity(): MovieEntity =
    MovieEntity(
        id = id,
        title = title,
        release_date = release_date,
        vote_average = vote_average,
        poster_path = poster_path,
        isFavorite = isFavorite,
        remoteIndex = remoteIndex
    )