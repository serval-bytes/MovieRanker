package com.nmccloud.project3.ui.info

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nmccloud.project3.R

class InfoFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //from what I understood of the project requirements, home/main shouldn't be accessible via a global action,
        //so it only makes sense that we hide the menu items in info and settings and restrict the user to
        //the back button
        activity?.findViewById<View>(R.id.nav_view)?.isVisible = false
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

}