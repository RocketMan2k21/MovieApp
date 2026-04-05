package com.romahduda.movies30.presentation.movieList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.romahduda.movies30.domain.models.Movie

@Composable
fun LikedListContent(
    likedMovies: List<Movie>,
    modifier : Modifier = Modifier,
    navigateToMoviesDetailsScreen: (Int) -> Unit
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .animateContentSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(likedMovies) { movie ->
            MovieItem(
                movie,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navigateToMoviesDetailsScreen(movie.id) }
            )
        }
    }
}
