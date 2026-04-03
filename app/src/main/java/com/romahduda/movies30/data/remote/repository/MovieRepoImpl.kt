package com.romahduda.movies30.data.remote.repository

import androidx.paging.Pager
import androidx.paging.map
import com.romahduda.movies30.BuildConfig
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.remote.api.MoviesApi
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.mappers.toMovie
import com.romahduda.movies30.domain.mappers.toMovieDetails
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val pager: Pager<Int, MovieEntity>
) : MovieRepo {

    override fun getPagingMovieFlow() = pager
        .flow
        .map { pager ->
            pager.map {
                it.toMovie()
            }
        }

    override fun getMovieById(movieId: Int) = flow {
        emit(
            moviesApi
                .getMovieById(movieId, BuildConfig.MOVIES_API_KEY)
                .toMovieDetails()
        )
    }
}