package com.romahduda.movies30.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.romahduda.movies30.domain.models.MovieDetails
import com.romahduda.movies30.navigation.destinations.movieDetailsComposable
import com.romahduda.movies30.navigation.destinations.movieListComposable
import com.romahduda.movies30.presentation.movieList.MovieAppBar
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel
import com.romahduda.movies30.presentation.viewmodels.UiEvent
import com.romahduda.movies30.util.Constants
import com.romahduda.movies30.util.Constants.MOVIES_DETAILS_SCREEN

import com.romahduda.movies30.util.Constants.MOVIES_SCREEN
import com.romahduda.movies30.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavigation(
    navController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    val currentMovie by movieViewModel.movieDetails.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    val screenName = remember(currentRoute, currentMovie) {
        mapScreenName(currentRoute, currentMovie)
    }
    val showTopBar = currentRoute?.equals(MOVIES_DETAILS_SCREEN) == false

    LaunchedEffect(true) {
        movieViewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = if (showTopBar) {
            Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        } else {
            Modifier
        },
        topBar = {
            if(showTopBar) {
                MovieAppBar(title = screenName, scrollBehavior = scrollBehavior)
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = MOVIES_SCREEN
        ) {
            movieListComposable(
                navigateToMoviesDetailsScreen = screen.movieDetails,
                sharedViewModel = movieViewModel,
            )
            movieDetailsComposable(
                sharedViewModel = movieViewModel
            )
        }
    }
}

private fun mapScreenName(
    currentRoute: String?,
    currentMovie: UiState<MovieDetails>
): String = when (currentRoute) {
    null -> ""
    MOVIES_SCREEN -> "Home"
    MOVIES_DETAILS_SCREEN -> {
        (currentMovie as? UiState.Success)?.data?.title ?: "Movie Details"
    }

    else -> "Movie App"
}