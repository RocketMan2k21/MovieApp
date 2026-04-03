package com.romahduda.movies30.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.romahduda.movies30.data.remote.repository.MovieRepo
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import com.romahduda.movies30.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<UiState<MovieDetails>>(UiState.Idle)
    val movieDetails: StateFlow<UiState<MovieDetails>> = _movieDetails

    val moviesPagingFlow: Flow<PagingData<Movie>> = movieRepo
        .getPagingMovieFlow()
        .cachedIn(viewModelScope)

    fun getMovieById(movieId: Int) {
        _movieDetails.value = UiState.Loading
        viewModelScope.launch {
            movieRepo.getMovieById(movieId)
                .catch { e ->
                    _movieDetails.value = UiState.Error(e)
                }
                .collect {
                    _movieDetails.value = UiState.Success(it)
                }
        }
    }
}