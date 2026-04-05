package com.romahduda.movies30.data.remote.repository

import androidx.paging.Pager
import androidx.paging.map
import com.romahduda.movies30.BuildConfig
import com.romahduda.movies30.data.local.dao.MovieDao
import com.romahduda.movies30.data.local.entity.LikedMovieEntity
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.remote.api.MoviesApi
import com.romahduda.movies30.domain.mappers.toMovie
import com.romahduda.movies30.domain.mappers.toMovieDetails
import com.romahduda.movies30.domain.models.Movie
import com.romahduda.movies30.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MovieRepoImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao,
    private val pager: Pager<Int, MovieEntity>
) : MovieRepo {

    override fun getPagingMovieFlow() = pager
        .flow
        .map { pager ->
            pager.map {
                it.toMovie()
            }
        }

    override fun getMovieList(): Flow<List<Movie>> {
        return movieDao.getAllMoviesFlow().map { it.map { movie -> movie.toMovie() } }
    }

    override suspend fun makeMovieFavorite(movieId: Int) {
        movieDao.insertLikedEntry(LikedMovieEntity(id = movieId))
    }

    override suspend fun makeMovieUnfavorite(movieId: Int): Int {
        return movieDao.removeLikedEntry(movieId)
    }

    override suspend fun getMovieById(movieId: Int): Flow<MovieDetails> = flow {
        val baseMovieDetails = moviesApi
            .getMovieById(movieId, BuildConfig.MOVIES_API_KEY)
            .toMovieDetails()

        val favoriteFlow = movieDao.isMovieLikedFlow(movieId)

        emitAll(
            favoriteFlow.map { isLiked ->
                baseMovieDetails.copy(isFavourite = isLiked)
            }
        )
    }

    override fun getLikedMovieEntries() : Flow<List<LikedMovieEntity>> {
        return movieDao.selectLikedEntries()
    }
}