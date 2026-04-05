package com.romahduda.movies30.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romahduda.movies30.data.local.dao.MovieDao
import com.romahduda.movies30.data.local.dao.MovieRemoteKeysDao
import com.romahduda.movies30.data.local.entity.LikedMovieEntity
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.local.entity.MovieRemoteKeys

@Database(entities = [MovieEntity::class, MovieRemoteKeys::class, LikedMovieEntity::class], version = 5)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun remoteKeysDao() : MovieRemoteKeysDao
}