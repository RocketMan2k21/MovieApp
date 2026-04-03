package com.romahduda.movies30.data.local

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.romahduda.movies30.BuildConfig
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.local.entity.MovieRemoteKeys
import com.romahduda.movies30.data.remote.api.MoviesApi
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.domain.mappers.toMovieEntity
import com.romahduda.movies30.util.MovieDatabase
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val db : MovieDatabase,
    private val movieApi : MoviesApi
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
       return try {
           val page = when (loadType) {
               LoadType.REFRESH -> 1
               LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
               LoadType.APPEND -> {
                   val lastItem = state.lastItemOrNull()
                   if (lastItem == null) {
                       return MediatorResult.Success(endOfPaginationReached = false)
                   }
                   val remoteKeys = db.remoteKeysDao().getRemoteKeysForMovie(lastItem.id)

                   val nextKey = remoteKeys?.nextKey
                       ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                   nextKey
               }
           }

           val response = movieApi.getMoviesByPage(page, BuildConfig.MOVIES_API_KEY)
           val endOfPaginationReached = page > (response.totalPages ?: 1)

           db.withTransaction {
               if (loadType == LoadType.REFRESH) {
                   db.movieDao().deleteAll()
                   db.remoteKeysDao().clearRemoteKeys()
               }

               val prevKey = if (page == 1) null else page - 1
               val nextKey = if (endOfPaginationReached) null else page + 1

               val keys = response.results.map {
                   MovieRemoteKeys(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
               }

               db.remoteKeysDao().insertAll(keys)
               db.movieDao().insertAll(response.results.map { it.toMovieEntity() })
           }

           MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
       } catch (exception: Exception) {
           MediatorResult.Error(exception)
       }
    }
}