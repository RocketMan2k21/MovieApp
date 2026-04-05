package com.romahduda.movies30.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.romahduda.movies30.data.local.entity.LikedMovieEntity
import com.romahduda.movies30.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY remoteIndex ASC")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movie_table ORDER BY remoteIndex ASC")
    fun getAllMoviesFlow() : Flow<List<MovieEntity>>

    @Query("SELECT * FROM liked_movies_table")
    fun selectLikedEntries(): Flow<List<LikedMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedEntry(entry: LikedMovieEntity)

    @Query("DELETE FROM liked_movies_table WHERE movie_id = :movieId")
    suspend fun removeLikedEntry(movieId: Int) : Int

    @Query("SELECT EXISTS(SELECT 1 FROM liked_movies_table WHERE movie_id = :movieId)")
    fun isMovieLikedFlow(movieId: Int): Flow<Boolean>

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies : List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)
}