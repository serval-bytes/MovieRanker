package com.nmccloud.project3.ui.home
/* Large portions of picasso/camera
 * usage code taken from Instructor Roolfs'
 * MediaTrackerDemo02 project
 */

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nmccloud.project3.R
import com.nmccloud.project3.database.Movie
import com.nmccloud.project3.ui.SharedViewModel
import com.nmccloud.project3.MainActivity.Companion.YEARS
import com.nmccloud.project3.MainActivity.Companion.YEAR_ZERO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.add_movie_dialog.*
import kotlinx.android.synthetic.main.movie_item.*
import java.io.File
import java.util.*

class AddMovieDialog : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

    private var newMovie: Movie = Movie()
    private var uuid = UUID.randomUUID()

    private val vm: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_movie_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // workaround to show full dialog in landscape mode (source: A. Roolf)
        dialog?.setOnShowListener {
            val dlg = dialog as BottomSheetDialog
            val bottomSheetInternal = dlg.findViewById<View>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal).peekHeight =
                resources.displayMetrics.heightPixels
        }

        genre_spinner.onItemSelectedListener = this
        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, YEARS)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        year_spinner.adapter = aa
        year_spinner.onItemSelectedListener = this
        rating_spinner.onItemSelectedListener = this
        notes_editText.imeOptions = EditorInfo.IME_ACTION_DONE
        notes_editText.inputType = InputType.TYPE_CLASS_TEXT

        cancel_button.setOnClickListener{
            dismiss()
        }

        done_button.setOnClickListener{
            onDone()
            dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDetach(){
        super.onDetach()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent){
            genre_spinner -> newMovie.genre = position
            year_spinner -> newMovie.yearReleased = position
            rating_spinner -> newMovie.rating = position
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun cameraPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun onDone(){
        newMovie.uuid = uuid
        newMovie.director = director_editText.text.toString()
        newMovie.title = title_editText.text.toString()
        newMovie.notes = notes_editText.text.toString()
        newMovie.yearReleased = YEAR_ZERO + (120 - newMovie.yearReleased) //required since the list is in reverse
        vm.addMovie(newMovie)
    }

    companion object {
        private const val TAG = "AddMovieDialog"
        private const val REQUEST_PHOTO = 0

        fun showDialog(fragmentManager: FragmentManager) {
            val dialog = AddMovieDialog()
            dialog.isCancelable = false
            dialog.show(
                fragmentManager,
                TAG
            )
        }
    }
}