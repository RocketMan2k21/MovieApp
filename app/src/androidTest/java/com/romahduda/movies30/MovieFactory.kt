package com.romahduda.movies30

import com.romahduda.movies30.data.remote.dto.MovieDetailsDto
import com.romahduda.movies30.data.remote.dto.MovieDto
import java.util.concurrent.atomic.AtomicInteger

class MovieFactory {
    private val counter = AtomicInteger(0)
    fun createMovie(movieTitle: String): MovieDto {
        val id = counter.incrementAndGet()
        val movie = MovieDto(
            id = id,
            posterPath = "poster_path $id",
            releaseDate = "release_date $id",
            title = movieTitle,
            voteAverage = 10.0
        )
        return movie
    }

    fun createDetailedMovie(): MovieDetailsDto {
        val id = counter.incrementAndGet()
        val movie = MovieDetailsDto(
            id = id,
            posterPath = "poster_path $id",
            releaseDate = "release_date $id",
            title = "title $id",
            voteAverage = 10.0,
            overview = "",
            runtime = 1,
            budget = 1,
            revenue = 1,
            tagline = ""
        )
        return movie
    }
}