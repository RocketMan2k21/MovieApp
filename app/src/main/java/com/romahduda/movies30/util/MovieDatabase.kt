package com.romahduda.movies30.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romahduda.movies30.data.local.dao.MovieDao
import com.romahduda.movies30.data.local.dao.MovieRemoteKeysDao
import com.romahduda.movies30.data.local.entity.MovieDetailsEntity
import com.romahduda.movies30.data.local.entity.MovieEntity
import com.romahduda.movies30.data.local.entity.MovieRemoteKeys

@Database(entities = [MovieEntity::class, MovieDetailsEntity::class, MovieRemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun remoteKeysDao() : MovieRemoteKeysDao
}