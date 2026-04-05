package com.romahduda.movies30.presentation.movieDetails

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.romahduda.movies30.R
import com.romahduda.movies30.domain.mappers.toMovieUpdate
import com.romahduda.movies30.domain.models.MovieDetails
import com.romahduda.movies30.domain.models.MovieToUpdate
import com.romahduda.movies30.util.Constants.IMAGE_TMDB_BASE_URL
import com.romahduda.movies30.util.UiState
import kotlinx.coroutines.delay

@Composable
fun MovieDetailsScreen(
    movie: UiState<MovieDetails>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (MovieToUpdate) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (movie is UiState.Success) {
            Log.i("MovieDetailsScreen", "Selected Movie: $movie")
            DisplayMovie(movie.data, modifier, onFavoriteClick = onFavoriteClick)
        }
        if (movie is UiState.Error) {
            ErrorContent(modifier)
        }
        if (movie is UiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.error_loading_specificMovie),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun DisplayMovie(
    movie: MovieDetails,
    modifier: Modifier = Modifier,
    onFavoriteClick: (MovieToUpdate) -> Unit,
) {
    val isClicked = remember { mutableStateOf(false) }
    val isStarScaled by animateDpAsState(targetValue = if (isClicked.value) 36.dp else 24.dp, label = "star_scale")
    val scrollState = rememberScrollState()

    val headerHeight = 450.dp

    LaunchedEffect(isClicked.value) {
        if (isClicked.value) {
            delay(200)
            isClicked.value = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = IMAGE_TMDB_BASE_URL + movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .graphicsLayer {
                    translationY = -scrollState.value * 0.5f
                    alpha = 1f - (scrollState.value / (headerHeight.toPx() * 1.2f)).coerceIn(0f, 1f)
                },
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(headerHeight - 30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = movie.tagline,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = "Rating: ${movie.voteAverage}", modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Release: ${movie.releaseDate}", modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Budget: \$${movie.budget}", modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Runtime: ${movie.runtime} min", modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Revenue: \$${movie.revenue}", modifier = Modifier.padding(bottom = 16.dp))

                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }

        FloatingActionButton(
            onClick = {
                isClicked.value = true
                onFavoriteClick(movie.toMovieUpdate())
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp)
                .graphicsLayer {
                    val scrollPx = scrollState.value.toFloat()
                    val offset = (headerHeight - 60.dp).toPx()

                    translationY = if (scrollPx < offset) {
                        offset - scrollPx
                    } else {
                        0f
                    }
                }
        ) {
            Icon(
                Icons.Rounded.Star,
                modifier = Modifier.size(isStarScaled),
                tint = if (movie.isFavourite) Color.White else Color.LightGray,
                contentDescription = "Favorite"
            )
        }
    }
}

@Preview
@Composable
fun PreviewMovieDetails() {
    MovieDetailsScreen(
        movie = UiState.Success(
            MovieDetails(
                id = 1,
                posterPath = "poster path",
                releaseDate = "2024-24-24",
                title = "Bad Boys",
                voteAverage = 10.0,
                overview = "Some cool movie",
                runtime = 999,
                budget = 99999,
                revenue = 888888,
                tagline = "Bad boys, bad boys",
                isFavourite = true
            )
        ),
        onFavoriteClick = {}
    )
}

@Preview
@Composable
fun PreviewError() {
    ErrorContent()
}