package com.nmccloud.project3.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nmccloud.project3.MainActivity
import com.nmccloud.project3.R
import com.nmccloud.project3.database.Movie
import com.nmccloud.project3.databinding.MovieItemBinding
import com.nmccloud.project3.ui.SharedViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var movieRecycler: RecyclerView
    private lateinit var movieAdapter: MovieRecyclerAdapter

    private val vm: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.menu?.get(2)?.setOnMenuItemClickListener {
            deleteAllAlert()
            true
        }
        activity?.findViewById<View>(R.id.nav_view)?.isVisible = true
        activity?.findViewById<View>(R.id.delete_all)?.isVisible = true
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.movies.observe(viewLifecycleOwner, Observer { movies ->
            movieAdapter.updateMovies(movies)
        })

        movieAdapter = MovieRecyclerAdapter()
        movieRecycler = view.findViewById(R.id.movie_recylerview)
        movieRecycler.layoutManager = LinearLayoutManager(context)
        movieRecycler.adapter = movieAdapter

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val movie = movieAdapter.getMovieAtPosition(viewHolder.adapterPosition)
                    movieDeletedAlert(movie)
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(movieRecycler)
        add_movie_fab.setOnClickListener{
            AddMovieDialog.showDialog(requireActivity().supportFragmentManager)
        }
    }

    private fun movieDeletedAlert(deletedMovie: Movie)
    {
        val msg = resources.getString(R.string.movie_deleted_alert,
            deletedMovie.title + " (" + deletedMovie.yearReleased.toString() + ")")
        val builder = android.app.AlertDialog.Builder(context)
        with(builder) {
            setTitle(R.string.alert)
            setMessage(msg)
            setNegativeButton("Undo", null)
            setIcon(R.drawable.ic_notifications_black_24dp)
            setPositiveButton(R.string.ok, DialogInterface.OnClickListener
            { _, _ ->
                vm.deleteMovie(deletedMovie)
            })
            show()
        }
    }

    private fun deleteAllAlert()
    {
        val msg = "WARNING: You are about to delete all movies from the list. Continue?"
        val builder = android.app.AlertDialog.Builder(context)
        with(builder) {
            setTitle(R.string.alert)
            setMessage(msg)
            setNegativeButton("Cancel", null)
            setIcon(R.drawable.ic_notifications_black_24dp)
            setPositiveButton(R.string.ok, DialogInterface.OnClickListener
            {   _, _ ->
                vm.deleteAllMovies()
            })
            show()
        }
    }

     inner class MovieRecyclerHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        override fun onClick(v: View?)
        {
            Log.d("asd", "tap")
        }

        fun bind(movie: Movie) {
            binding.apply {
                binding.movie = movie
                binding.watchedCheckBox.isChecked = movie.watched
                executePendingBindings()
            }
        }
    }

    private inner class MovieRecyclerAdapter : RecyclerView.Adapter<MovieRecyclerHolder>() {
        private var movies: List<Movie> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecyclerHolder =
            MovieRecyclerHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.movie_item, parent, false
                )
            )

        override fun getItemCount() = movies.size

        override fun onBindViewHolder(holderMovie: MovieRecyclerHolder, position: Int) {
            val movie = movies[position]
            holderMovie.itemView.setOnClickListener{
                Log.d("YE", "TAP")
                vm.setSelectedMovie(movie)
                (activity as MainActivity?)?.navToDetailView()
            }
            holderMovie.bind(movie)
        }

        fun updateMovies(newMovies: List<Movie>) {
            this.movies = newMovies
            notifyDataSetChanged()
        }

        fun getMovieAtPosition(position: Int): Movie {
            return movies[position]
        }
    }
}