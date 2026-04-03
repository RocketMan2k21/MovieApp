package com.romahduda.movies30

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.paging.testing.asSnapshot
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.mappers.toMovie
import com.romahduda.movies30.domain.mappers.toMovieDetails
import com.romahduda.movies30.data.remote.repository.MovieRepoImpl
import com.romahduda.movies30.util.FakeMovieApi
import com.romahduda.movies30.util.FakeMoviePagingSource
import com.romahduda.movies30.util.MainCoroutineScopeRule
import com.romahduda.movies30.util.MovieFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

class MovieRepoImplTest {

    @get:Rule
    val mainRule = MainCoroutineScopeRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


    private val movieFactory = MovieFactory()

    private val mockMovies = listOf(
        movieFactory.createMovie(),
        movieFactory.createMovie(),
        movieFactory.createMovie(),
        movieFactory.createMovie()
    )

    private val mockMovieDetails = movieFactory.createDetailedMovie()

    private lateinit var fakeApi: FakeMovieApi
    private lateinit var movieRepo: MovieRepoImpl

    private lateinit var pagingSource: PagingSource<Int, MovieDto>

    @Before
    fun setup() {
        fakeApi = FakeMovieApi().apply {
            mockMovies.forEach { movie -> addMovie(movie) }
            addMovieById(mockMovieDetails)
        }

        pagingSource = FakeMoviePagingSource(mockMovies)

        val fakePager = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource }
        )

        movieRepo = MovieRepoImpl(fakeApi, fakePager)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return list of movies when getPagingMovieFlow is called`() = runTest {

        val movies = movieRepo.getPagingMovieFlow().asSnapshot()

        assertEquals(
            mockMovies.map { movie ->
                movie.toMovie()
            },
            movies
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getPagingMovieFlow_error() = runTest {

        pagingSource = FakeMoviePagingSource(mockMovies, true)
        val pager = TestPager(PagingConfig(pageSize = 20), pagingSource)

        val result = pager.refresh()

        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMovieById_success() = runTest {
        val movieDetails = movieRepo.getMovieById(mockMovieDetails.id).first()
        assertEquals(mockMovieDetails.toMovieDetails(), movieDetails)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMovieById_error() = runTest {
        fakeApi.setReturnsError()

        try {
            movieRepo.getMovieById(mockMovieDetails.id).collect()
            fail("Expected an HttpException to be thrown")
        } catch (e: HttpException) {
            // Expected exception
        }
    }
}



