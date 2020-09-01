package com.nmccloud.project3.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class MovieRepository private constructor(context: Context){

    private val database: MovieDatabase = Room.databaseBuilder(
        context.applicationContext,
        MovieDatabase::class.java,
        "movieranker_database"
    )
        .build()

    private val movieDao = database.movieDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun addMovie(movie: Movie) {
        executor.execute {
            movieDao.addMovie(movie)
        }
    }

    fun updateMovie(movie: Movie) {
        executor.execute {
            movieDao.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        executor.execute {
            movieDao.deleteMovie(movie)
        }
    }

    fun deleteAll() {
        executor.execute {
            movieDao.deleteAll()
        }
    }

    fun getMovie(id: Long): LiveData<Movie?> = movieDao.getMovie(id)

    fun getAllMovies(): LiveData<List<Movie>> = movieDao.getAllMovies()

    companion object {

        private var INSTANCE: MovieRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(context)
            }
        }

        fun get(): MovieRepository {
            return INSTANCE
                ?: throw IllegalStateException("MovieRepository must be initialized.")
        }
    }
}