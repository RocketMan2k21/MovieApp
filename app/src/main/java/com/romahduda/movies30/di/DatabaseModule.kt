package com.romahduda.movies30.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.romahduda.movies30.util.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "movie_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
}