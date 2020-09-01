package com.nmccloud.project3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.nmccloud.project3.MainActivity
import com.nmccloud.project3.R
import com.nmccloud.project3.database.Movie
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    private val vm: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.findViewById<View>(R.id.nav_view)?.isVisible = true
        activity?.findViewById<View>(R.id.delete_all)?.isVisible = false
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.selectedMovie.observe(viewLifecycleOwner, Observer<Movie>{
            tapToEdit.text = "Your Notes for ${it.title} (Tap to Edit):"
            director_textView.text = it.director
            yourNotes.setText(it.notes)
            genre_imageView.setImageResource(it.typeImage)
            your_rating.text = "Your rating: " + it.ratingText
            title_textView.text = "${it.title} (${it.yearReleased})"
        })
    }

    override fun onStart() {
        super.onStart()
          reviews_Button.setOnClickListener {
              (activity as MainActivity?)?.navToReviewView()
          }
    }

}