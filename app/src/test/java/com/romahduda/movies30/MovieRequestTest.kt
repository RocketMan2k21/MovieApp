package com.romahduda.movies30

import com.romahduda.movies30.util.FakeMovieApi
import com.romahduda.movies30.util.MovieFactory
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import kotlin.test.assertEquals

class MovieRequestTest {
    private val movieFactory = MovieFactory()
    val movie = movieFactory.createDetailedMovie()
    private val fakeApi = FakeMovieApi().apply {
        addMovieById(movie)
    }

    @Test
    fun movieApi_MovieByIdRequested_SelectedMovieDetailsReceived() = runTest {
        assertEquals(movie, fakeApi.getMovieById(1, "API_KEY"))
    }

    @Test
    fun movieApi_MovieByIdRequested_HttpExceptionReceived() {
        fakeApi.setReturnsError()

        runTest {
            assertThrows<HttpException> { fakeApi.getMovieById(1, "API_KEY") }
        }
    }
}