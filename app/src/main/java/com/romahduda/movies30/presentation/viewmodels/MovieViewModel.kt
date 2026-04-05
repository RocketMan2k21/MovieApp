package com.romahduda.movies30.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.romahduda.movies30.data.remote.repository.MovieRepo
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import com.romahduda.movies30.domain.models.MovieToUpdate
import com.romahduda.movies30.util.DataResult
import com.romahduda.movies30.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _movieDetails = MutableStateFlow<UiState<MovieDetails>>(UiState.Idle)
    val movieDetails: StateFlow<UiState<MovieDetails>> = _movieDetails

    private var movieJob: Job? = null

    private val likedMovieEntries = movieRepo
        .getLikedMovieEntries()

    val moviesPagingFlow: Flow<PagingData<Movie>> = movieRepo
        .getPagingMovieFlow()
        .cachedIn(viewModelScope)

    val likedMovies: StateFlow<UiState<List<Movie>>> = movieRepo
        .getMovieList()
        .catch { throwable -> UiState.Error("Error while fetching favourite movies") }
        .combine(likedMovieEntries) { movieList, likedIds ->
            movieList.filter { movie -> likedIds.any { it.id == movie.id } }
        }
        .map { movieList ->
            if (movieList.isNotEmpty()) {
                UiState.Success(movieList)
            } else {
                UiState.Error("You don't have favourite movies")
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Idle)

    fun getMovieById(movieId: Int) {
        movieJob?.cancel()
        _movieDetails.value = UiState.Loading
        movieJob = viewModelScope.launch {
            movieRepo.getMovieById(movieId)
                .catch { e ->
                    _movieDetails.value = UiState.Error("Error fetching movie data")
                }
                .collect { result ->
                    when (result) {
                        is DataResult.Error -> {
                            _movieDetails.value = UiState.Error(result.message)
                        }
                        is DataResult.Success<MovieDetails> -> {
                            _movieDetails.value = UiState.Success(result.data)
                        }
                    }
                }
        }
    }

    fun switchFavoriteMovie(movie: MovieToUpdate) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                unlikeMovie(movie)
            } else {
                likeMovie(movie)
            }
        }
    }

    private suspend fun likeMovie(movie: MovieToUpdate) {
        try {
            movieRepo.makeMovieFavorite(movie.id)
            _eventFlow.emit(UiEvent.ShowSnackbar("${movie.title} is added to favourites"))
        } catch (e: Exception) {
            _eventFlow.emit(UiEvent.ShowSnackbar("Error adding to favourites"))
        }
    }

    private suspend fun unlikeMovie(movie: MovieToUpdate) {
        try {
            val id = movieRepo.makeMovieUnfavorite(movie.id)
            if (id != 0) {
                _eventFlow.emit(UiEvent.ShowSnackbar("${movie.title} is removed to favourites"))
            }
        } catch (e: Exception) {
            _eventFlow.emit(UiEvent.ShowSnackbar("Error adding removing from favourites"))
        }
    }


}

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
}