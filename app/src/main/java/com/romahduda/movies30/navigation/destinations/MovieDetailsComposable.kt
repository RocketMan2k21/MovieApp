package com.romahduda.movies30.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel
import com.romahduda.movies30.presentation.movieDetails.MovieDetailsScreen
import com.romahduda.movies30.util.Constants.MOVIES_ARGUMENT_KEY
import com.romahduda.movies30.util.Constants.MOVIES_DETAILS_SCREEN

fun NavGraphBuilder.movieDetailsComposable(
    sharedViewModel: MovieViewModel
) {
    composable(
        route = MOVIES_DETAILS_SCREEN, arguments = listOf(navArgument(MOVIES_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val movieId = navBackStackEntry.arguments?.getInt(MOVIES_ARGUMENT_KEY)

        LaunchedEffect(movieId) {
            movieId?.let { sharedViewModel.getMovieById(movieId = it) }
        }

        val selectedMovie by sharedViewModel.movieDetails.collectAsState()

        MovieDetailsScreen(
            movie = selectedMovie
        )
    }
}