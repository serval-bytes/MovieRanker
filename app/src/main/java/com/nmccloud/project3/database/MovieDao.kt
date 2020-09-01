package com.nmccloud.project3.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie_table WHERE id=(:id)")
    fun getMovie(id: Long): LiveData<Movie?>

    @Query("SELECT * FROM movie_table ORDER BY id DESC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE watched=(0) ORDER BY id DESC")
    fun getAllUnwatchedMovies(): LiveData<List<Movie>>

    @Query("DELETE FROM movie_table")
    fun deleteAll()
}