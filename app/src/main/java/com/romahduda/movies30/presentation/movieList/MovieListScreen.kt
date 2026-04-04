package com.romahduda.movies30.presentation.movieList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.romahduda.movies30.R
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.presentation.viewmodels.MovieViewModel
import kotlinx.coroutines.launch

enum class PagerTabs(val title: String) {
    POPULAR("Popular"),
    FAVORITE("Favourites"),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieScreen(
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    sharedViewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val movies = sharedViewModel.moviesPagingFlow.collectAsLazyPagingItems()
    val pagerState = rememberPagerState { PagerTabs.entries.size }
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            PagerTabs.entries.forEachIndexed { index, tab -> 
                Tab(
                    text = {
                        Text(text = tab.title)
                    },
                    selected = index == selectedTabIndex,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(tab.ordinal)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState
        ) { page ->
            if (page == PagerTabs.POPULAR.ordinal) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (movies.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                    if (movies.loadState.refresh is LoadState.Error && movies.itemCount < 1) {
                        ErrorNetworkContent(onRefresh = { movies.refresh() })
                    } else {
                        GridContent(
                            movies = movies,
                            onFavoriteClick = { movie: Movie -> },
                            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
                        )
                    }
                }
            } else if (page == PagerTabs.FAVORITE.ordinal) {

            }
        }
    }
}

@Composable
fun ErrorNetworkContent(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.error_loading_movieList)
        )
        Spacer(Modifier.height(10.dp))
        IconButton(
            onClick = onRefresh
        ) {
            Icon(Icons.Filled.Search, null)
        }
    }
}




