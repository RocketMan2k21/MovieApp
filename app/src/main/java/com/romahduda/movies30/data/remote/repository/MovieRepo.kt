package com.romahduda.movies30.data.remote.repository

import androidx.paging.PagingData
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    fun getMovieById(movieId: Int): Flow<MovieDetails>
    fun getPagingMovieFlow(): Flow<PagingData<Movie>>
}