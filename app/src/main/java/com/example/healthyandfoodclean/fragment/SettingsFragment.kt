package com.example.healthyandfoodclean.fragment


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import com.example.healthyandfoodclean.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)

        Toast.makeText(context, "These are your settings", Toast.LENGTH_SHORT).show()
    }

}