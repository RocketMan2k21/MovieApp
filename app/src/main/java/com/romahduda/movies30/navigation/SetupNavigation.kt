package com.romahduda.movies30.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.romahduda.movies30.navigation.destinations.movieDetailsComposable
import com.romahduda.movies30.navigation.destinations.movieListComposable
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel

import com.romahduda.movies30.util.Constants.MOVIES_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel()
) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = MOVIES_SCREEN
    ) {
        movieListComposable(
            navigateToMoviesDetailsScreen = screen.movieDetails,
            sharedViewModel = movieViewModel
        )
        movieDetailsComposable(
            sharedViewModel = movieViewModel
        )
    }
}