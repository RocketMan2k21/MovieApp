package com.romahduda.movies30.data.remote.repository

import androidx.paging.PagingData
import com.romahduda.movies30.data.local.entity.LikedMovieEntity
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    suspend fun getMovieById(movieId: Int): Flow<MovieDetails>
    fun getPagingMovieFlow(): Flow<PagingData<Movie>>
    fun getMovieList() : Flow<List<Movie>>
    suspend fun makeMovieFavorite(movieId : Int)
    suspend fun makeMovieUnfavorite(movieId : Int) : Int
    fun getLikedMovieEntries() : Flow<List<LikedMovieEntity>>
}