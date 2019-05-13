package com.example.popularmovies.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.popularmovies.data.main.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PopularMoviesDb : RoomDatabase() {

    abstract fun popularMoviesDao(): MoviesDao

    companion object {

        private val NAME = StringBuilder(PopularMoviesDb::class.java.simpleName).append(".db").toString()

        fun create(context: Context): PopularMoviesDb {
            return Room.databaseBuilder(context, PopularMoviesDb::class.java, NAME).build()
        }
    }

}