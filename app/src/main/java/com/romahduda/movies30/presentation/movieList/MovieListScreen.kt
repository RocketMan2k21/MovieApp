package com.romahduda.movies30.presentation.movieList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.romahduda.movies30.R
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel


@Composable
fun MovieScreen(
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    sharedViewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val movies = sharedViewModel.moviesPagingFlow.collectAsLazyPagingItems()

    Box(modifier = modifier.fillMaxSize()) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (movies.loadState.refresh is LoadState.Error && movies.itemCount < 1) {
            ErrorNetworkContent(modifier = modifier)
        } else {
            GridContent(
                movies = movies,
                navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ErrorNetworkContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.error_loading_movieList)
        )
    }
}




