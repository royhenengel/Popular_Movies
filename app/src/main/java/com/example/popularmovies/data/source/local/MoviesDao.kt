package com.example.popularmovies.data.source.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.popularmovies.data.main.entity.MovieEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInDb(movies: List<MovieEntity>)

    @Query("SELECT * FROM table_movies")
    fun getPopularMovies(): DataSource.Factory<Int, MovieEntity>

}