package com.romahduda.movies30.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romahduda.movies30.data.local.dao.MovieDao
import com.romahduda.movies30.data.local.dao.MovieDetailsDao
import com.romahduda.movies30.data.local.dao.MovieRemoteKeysDao
import com.romahduda.movies30.data.local.entity.LikedMovieEntity
import com.romahduda.movies30.data.local.entity.MovieDetailsEntity
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.local.entity.MovieRemoteKeys

@Database(entities = [MovieEntity::class, MovieDetailsEntity::class, MovieRemoteKeys::class, LikedMovieEntity::class], version = 4, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun movieDetailsDao() : MovieDetailsDao
    abstract fun remoteKeysDao() : MovieRemoteKeysDao
}