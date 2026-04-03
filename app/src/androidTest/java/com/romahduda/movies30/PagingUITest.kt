package com.romahduda.movies30

import FakeMyRepository
import android.util.Log
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PagingUITest {

    private val viewModel = MovieViewModel(
        movieRepo = FakeMyRepository()
    )

    @Test
    fun test_items_contain_one_to_sixty() = runTest {
        val items: Flow<PagingData<Movie>> = viewModel.moviesPagingFlow

        val itemsSnapshot: List<Movie> = items.asSnapshot {
            scrollTo(index = 30)
        }

        val expectedItems = (0..59).map {
            Movie(
                id = it,
                title = "Movie $it",
                vote_average = 5.0,
                release_date = "2022-01-01",
                poster_path = "/path$it"
            )
        }

        Log.d("test_items_contain_one_to_twenty", "Expected Items ${expectedItems.size}: $expectedItems")
        Log.d("test_items_contain_one_to_twenty", "Actual Items ${itemsSnapshot.size}: $itemsSnapshot")

        assertEquals(expectedItems, itemsSnapshot)
    }
}