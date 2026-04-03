package com.romahduda.movies30.presentation.movieDetails

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.romahduda.movies30.R
import com.romahduda.movies30.domain.models.MovieDetails
import com.romahduda.movies30.util.Constants.IMAGE_TMDB_BASE_URL
import com.romahduda.movies30.util.UiState

@Composable
fun MovieDetailsScreen(
    movie: UiState<MovieDetails>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (movie is UiState.Success) {
            Log.i("MovieDetailsScreen", "Selected Movie: $movie")
            DisplayMovie(movie.data, modifier)
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = IMAGE_TMDB_BASE_URL + movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = movie.title,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = movie.tagline,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Rating: ${movie.voteAverage}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${movie.releaseDate}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Budget: \$${movie.budget}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Runtime: ${movie.runtime} minutes",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Revenue: \$${movie.revenue}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = movie.overview,
                modifier = Modifier.padding(bottom = 8.dp)
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
                tagline = "Bad boys, bad boys"
            )
        )
    )
}

@Preview
@Composable
fun PreviewError() {
    ErrorContent()
}