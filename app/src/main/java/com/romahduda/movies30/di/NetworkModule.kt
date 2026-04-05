package com.romahduda.movies30.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import com.romahduda.movies30.data.local.MovieRemoteMediator
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.remote.api.MoviesApi
import com.romahduda.movies30.data.remote.dto.MovieDto
import com.romahduda.movies30.data.remote.repository.MovieRepo
import com.romahduda.movies30.data.remote.repository.MovieRepoImpl
import com.romahduda.movies30.util.Constants.MOVIE_BASE_URL
import com.romahduda.movies30.util.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideRepoImpl(
        moviesApi: MoviesApi,
        database: MovieDatabase,
        pager: Pager<Int, MovieEntity>
    ): MovieRepo = MovieRepoImpl(moviesApi, database.movieDao(), pager)

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviePager(db: MovieDatabase, moviesApi: MoviesApi): Pager<Int, MovieEntity> {
        val pagingSourceFactory = { db.movieDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = MovieRemoteMediator(db, moviesApi)
        )
    }
}