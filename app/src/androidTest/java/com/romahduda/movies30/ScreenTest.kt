package com.romahduda.movies30


import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.romahduda.movies30.navigation.Screens
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Modifier

@Composable
fun TestNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") { Text("Start Screen") }
        composable("movies/") { Text("Movies Screen") }
        composable("movies/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            Text("Movie Details Screen for $movieId")
        }
    }
}

class ScreensTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testMoviesNavigation() {
        val navController = TestNavHostController(composeTestRule.activity)
        val screens = Screens(navController)

        composeTestRule.activity.setContent {
            TestNavGraph(navController = navController)
        }

        screens.movies()

        assertEquals("movies/", navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun testMovieDetailsNavigation() {

        val navController = TestNavHostController(composeTestRule.activity)
        val screens = Screens(navController)

        composeTestRule.setContent {
            screens.movieDetails(42)
        }

        screens.movieDetails(42)

        assertEquals("movies/42", navController.currentBackStackEntry?.destination?.route)
    }
}

