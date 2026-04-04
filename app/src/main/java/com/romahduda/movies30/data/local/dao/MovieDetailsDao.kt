package com.romahduda.movies30.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.romahduda.movies30.data.local.entity.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(movieDetailsEntity: MovieDetailsEntity)

    @Query("SELECT * FROM movie_details WHERE id=:id")
    fun getMovieById(id : Int) : Flow<MovieDetailsEntity>

    @Query("UPDATE movie_table SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun updateMovieTableFavorite(movieId: Int): Int

    // 2. Update the details table
    @Query("UPDATE movie_details SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun updateMovieDetailsFavorite(movieId: Int): Int

    @Transaction
    suspend fun switchFavoriteMovie(movieId: Int): Int {
        val updatedRows = updateMovieTableFavorite(movieId)
        updateMovieDetailsFavorite(movieId)
        return updatedRows
    }
}
