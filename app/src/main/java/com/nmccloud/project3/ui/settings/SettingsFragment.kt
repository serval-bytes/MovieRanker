package com.nmccloud.project3.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nmccloud.project3.MainActivity.Companion.SHOW_HIDE_WATCHED
import com.nmccloud.project3.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //as with InfoFragment, I assume we do not want to see menu items here
        activity?.findViewById<View>(R.id.nav_view)?.isVisible = false
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show_hide_switch.isChecked = prefs.getBoolean(SHOW_HIDE_WATCHED, false)
        show_hide_switch.setOnCheckedChangeListener { _, isChecked ->
            with(prefs.edit()) {
                putBoolean(SHOW_HIDE_WATCHED, isChecked)
                apply()
            }
        }

        }
    }