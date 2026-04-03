package com.romahduda.movies30.navigation

import androidx.navigation.NavHostController
import com.romahduda.movies30.util.Constants.MOVIES_SCREEN

class Screens(navController: NavHostController) {
    val movies: () -> Unit = {
        navController.navigate("movies/") {
            popUpTo(MOVIES_SCREEN) { inclusive = true }
        }
    }

    val movieDetails: (Int) -> Unit = { movieId ->
        navController.navigate("movies/$movieId")
    }
}