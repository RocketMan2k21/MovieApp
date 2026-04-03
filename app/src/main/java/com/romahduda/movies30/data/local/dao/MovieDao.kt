package com.romahduda.movies30.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.romahduda.movies30.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY remoteIndex ASC")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movie_table where id=:id")
    fun getMovieById(id : Int) : Flow<MovieEntity>

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies : List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)
}