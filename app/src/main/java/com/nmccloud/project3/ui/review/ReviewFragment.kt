package com.nmccloud.project3.ui.review

import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.nmccloud.project3.R
import com.nmccloud.project3.database.Movie
import com.nmccloud.project3.ui.SharedViewModel
import kotlinx.android.synthetic.main.review_view.*

/* Originally implemented using SafeArgs,
   refactored to use shared ViewModel to
   be consistent with DetailFragment
   and for convenience
 */

class ReviewFragment : Fragment() {

    private val vm: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.review_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.selectedMovie.observe(viewLifecycleOwner, Observer<Movie> {
            (activity as AppCompatActivity).supportActionBar?.title = "Reviews for ${it.title} ${it.yearReleasedText}"
            review_View.webViewClient = WebViewClient()
            review_View.settings.javaScriptEnabled = true
            val titleTween = "/m/" + it.title.toLowerCase().replace("[-:\\s]".toRegex(), "_")
            val titleFinal = titleTween.replace("_+".toRegex(), "_") //kind of messy but it'll do for now...
            Log.d("title: ", titleFinal)
            review_View.loadUrl("https://rottentomatoes.com$titleFinal")
            })
    }
}
