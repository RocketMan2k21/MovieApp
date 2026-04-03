package com.romahduda.movies30

import com.romahduda.movies30.data.remote.dto.MovieDetailsDto
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.mappers.toMovie
import com.romahduda.movies30.domain.mappers.toMovieDetails
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DtoConversionTest {

    @Test
    fun testMovieDtoToMovie() {
        val movieDto = MovieDto(
            id = 1,
            title = "Test Movie",
            releaseDate = "2024-07-25",
            voteAverage = 8.5,
            posterPath = "/testPosterPath.jpg"
        )

        val movie = movieDto.toMovie()

        assertEquals(movie.id, 1)
        assertEquals(movie.title, "Test Movie")
        assertEquals(movie.release_date, "2024-07-25")
        assertEquals(movie.vote_average, 8.5)
        assertEquals(movie.poster_path, "/testPosterPath.jpg")
    }

    @Test
    fun testMovieDetailsDtoToMovieDetails() {
        val movieDetailsDto = MovieDetailsDto(
            id = 1,
            title = "Test Movie",
            releaseDate = "2024-07-25",
            voteAverage = 8.5,
            posterPath = "/testPosterPath.jpg",
            overview = "Test Overview",
            runtime = 120,
            budget = 100000000,
            revenue = 500000000,
            tagline = "Test Tagline"
        )

        val movieDetails = movieDetailsDto.toMovieDetails()

        assertEquals(movieDetails.id, 1)
        assertEquals(movieDetails.title, "Test Movie")
        assertEquals(movieDetails.releaseDate, "2024-07-25")
        assertEquals(movieDetails.voteAverage, 8.5)
        assertEquals(movieDetails.posterPath, "/testPosterPath.jpg")
        assertEquals(movieDetails.overview, "Test Overview")
        assertEquals(movieDetails.runtime, 120)
        assertEquals(movieDetails.budget, 100000000)
        assertEquals(movieDetails.revenue, 500000000)
        assertEquals(movieDetails.tagline, "Test Tagline")
    }
}
