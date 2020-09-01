package com.nmccloud.project3.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nmccloud.project3.database.Movie
import com.nmccloud.project3.database.MovieRepository

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    init{
        MovieRepository.initialize(application)
    }

    private val movieRepository = MovieRepository.get()
    val movies = movieRepository.getAllMovies()

    private val _selectedMovie = MutableLiveData<Movie>()
    var selectedMovie = _selectedMovie

    fun addMovie(newMovie: Movie) {
        movieRepository.addMovie(newMovie)
    }

    fun deleteMovie(movie: Movie) {
        movieRepository.deleteMovie(movie)
    }

    fun deleteAllMovies(){
        movieRepository.deleteAll()
    }

    fun setSelectedMovie(movie: Movie)
    {
        _selectedMovie.value = movie
    }
}