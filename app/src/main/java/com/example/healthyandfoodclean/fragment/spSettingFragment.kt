package com.example.healthyandfoodclean.fragment


import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.healthyandfoodclean.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class spSettingFragment : Fragment() {
private lateinit var fab_settings : FloatingActionButton
private lateinit var tv_signature: TextView
    private lateinit var  tv_reply:TextView
    private lateinit var tv_sync:TextView
    private lateinit var  tv_notifications:TextView
    private lateinit var pb_volume : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sp_setting, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab_settings.setOnClickListener {

            findNavController().navigate(R.id.action_HomeActivity_to_SettingFragment)
        }
        settings()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun settings() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val signature = sp.getString("signature", "")
        val defaultReplyAction = sp.getString("reply", "")
        val sync = sp.getBoolean("sync", true)
        val notifications = sp.getBoolean("notifications", true)
        val volume = sp.getInt("volume_notifications", 0)

        tv_signature.text = "Signature: $signature"
        tv_reply.text = "Default reply: $defaultReplyAction"
        tv_sync.text = "Sync: $sync"
        tv_notifications.text = "Disable notifications: $notifications"

        pb_volume.setProgress(volume, true)

    }

}

