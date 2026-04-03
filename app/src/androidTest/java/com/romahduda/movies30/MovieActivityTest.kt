package com.romahduda.movies30

import FakeMyRepository
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.romahduda.movies30.presentation.movieList.MovieScreen
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel
import com.romahduda.movies30.ui.theme.Movies30Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ActivityTest {

    @get:Rule val composeTestRule = createComposeRule()

    private val postFactory = MovieFactory()
    private lateinit var mockApi: FakeMovieApi
    private lateinit var mockRepository: FakeMyRepository
    private lateinit var mockViewModel: MovieViewModel

    @Before
    fun setup() {
        mockApi = FakeMovieApi().apply {
            addMovie(postFactory.createMovie("title 1"))
            addMovie(postFactory.createMovie("title 2"))
            addMovie(postFactory.createMovie("title 3"))
        }
        mockRepository = FakeMyRepository()
        ServiceLocator.initialize(mockApi, mockRepository)
        mockViewModel =  MovieViewModel(mockRepository)
    }

    @Test
    fun loadsTheDefaultResults() {
        composeTestRule.setContent {
            Movies30Theme {
                MovieScreen(
                    navigateToMoviesDetailsScreen = {},
                    sharedViewModel = mockViewModel
                )
            }
        }
        composeTestRule.onNodeWithText("title 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("title 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("title 3").assertIsDisplayed()
    }
}
