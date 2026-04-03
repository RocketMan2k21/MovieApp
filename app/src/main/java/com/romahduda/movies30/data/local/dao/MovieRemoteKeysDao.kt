package com.romahduda.movies30.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.romahduda.movies30.data.local.entity.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieRemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE movieId = :movieId")
    suspend fun getRemoteKeysForMovie(movieId: Int): MovieRemoteKeys?

    @Query("SELECT * FROM remote_keys ORDER BY movieId DESC LIMIT 1")
    suspend fun getLatestRemoteKey(): MovieRemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
