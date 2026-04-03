package com.romahduda.movies30

import com.romahduda.movies30.data.remote.api.MoviesApi
import com.romahduda.movies30.data.remote.repository.MovieRepo

object ServiceLocator {

    private var moviesApi: MoviesApi? = null
    private var movieRepository: MovieRepo? = null

    fun initialize(moviesApi: MoviesApi, movieRepository: MovieRepo) {
        ServiceLocator.moviesApi = moviesApi
        ServiceLocator.movieRepository = movieRepository
    }
}


