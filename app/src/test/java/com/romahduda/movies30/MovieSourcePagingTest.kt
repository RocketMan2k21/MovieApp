package com.romahduda.movies30

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.TestPager
import com.romahduda.movies30.data.remote.api.MoviePagingSource
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.util.FakeMovieApi
import com.romahduda.movies30.util.MovieFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test


class MovieSourcePagingTest {
    private val movieFactory = MovieFactory()

    private val mockMovies = listOf(
        movieFactory.createMovie(),
        movieFactory.createMovie(),
        movieFactory.createMovie(),
        movieFactory.createMovie(),
    )
    private val fakeApi = FakeMovieApi().apply {
        mockMovies.forEach { movie -> addMovie(movie) }
    }
    private val pagingSource = MoviePagingSource(
        fakeApi
    )

    private val pager = TestPager(PagingConfig(
        pageSize = 20
    ), pagingSource)


    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val pagingSourceLoadResult = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(mockMovies.size, pagingSourceLoadResult.data.size)
        assertEquals(mockMovies, pagingSourceLoadResult.data)
    }

    @Test
    fun test_consecutive_loads() = runTest {
        val page = with(pager) {
            refresh()
            append()
            append()
        } as PagingSource.LoadResult.Page

        assertEquals(page.data, mockMovies)
    }

    @Test
    fun refresh_returnError(){
        fakeApi.setReturnsError()

        runTest {
            val result = pager.refresh()
            assertTrue(result is PagingSource.LoadResult.Error)
        }
    }

    @Test
    fun test_getRefreshKey() {
        val movieList = mockMovies.mapIndexed { index, movie ->
            MovieDto(
                id = index,
                title = movie.title,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage
            )
        }

        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = movieList,
                    prevKey = 1,
                    nextKey = 3
                )
            ),
            anchorPosition = 1,
            config = PagingConfig(20),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(pagingState)

        assertEquals(2, refreshKey)
    }

    @Test
    fun getRefreshKey_withPrevKey_returnsPlusOne() {
        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(movieFactory.createMovie()),
                    prevKey = 1,
                    nextKey = null
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 1),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(pagingState)
        assertEquals(2, refreshKey)
    }

    @Test
    fun getRefreshKey_withNextKey_returnsMinusOne() {
        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(movieFactory.createMovie()),
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 1),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(pagingState)
        assertEquals(1, refreshKey)
    }
}