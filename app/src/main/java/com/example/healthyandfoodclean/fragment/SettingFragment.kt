package com.example.healthyandfoodclean.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.google.android.material.navigation.NavigationView
import android.media.AudioManager
import android.view.MenuItem
import androidx.preference.EditTextPreference
import com.example.healthyandfoodclean.*


class SettingFragment : AppCompatActivity() {
    private lateinit var nav_view: NavigationView
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var volumePreference: SeekBarPreference
    private lateinit var signature: EditTextPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_setting)
        var isDark = false
        drawer = findViewById(R.id.drawlayout)
        nav_view = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.open_nav, R.string.close_nav)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        nav_view.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_notification -> {
                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    val channelId = "Update Healthy and Food Clean"
                    val channelName = "Update Healthy and Food Clean"
                    val importance = NotificationManager.IMPORTANCE_HIGH

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(channelId, channelName, importance)
                        notificationManager.createNotificationChannel(channel)
                    }

                    val notificationBuilder = NotificationCompat.Builder(this, channelId)
                        .setContentTitle("Cập nhật ứng dụng")
                        .setContentText("Đã có phiên bản mới của ứng dụng, hãy cập nhật ngay!")
                        .setSmallIcon(R.drawable.ic_bell)
                        .setAutoCancel(true)

                    val notificationId = R.id.nav_notification
                    notificationManager.notify(notificationId, notificationBuilder.build())
                }

                R.id.nav_home ->
                    startActivity(
                        Intent(
                            this,
                            HomeActivity::class.java
                        )
                    )
                R.id.nav_water ->
                    startActivity(
                        Intent(
                            this,
                            DrinkWaterActivity::class.java
                        )
                    )
                R.id.nav_randomfood ->
                    startActivity(
                        Intent(
                            this,
                            EatClean::class.java
                        )
                    )
                R.id.nav_chatgpt ->
                    startActivity(
                        Intent(
                            this,
                            ChatGPTActivity::class.java
                        )
                    )
                R.id.nav_listening ->
                    startActivity(
                        Intent(
                            this,
                            ListenerActivity::class.java
                        )
                    )
                R.id.nav_switch->
                    try {
                        if (getWindow() != null && isDark) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            isDark = false;
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            isDark = true;
                        }
                    } catch (e: Exception) {
                        //exception handling if any
                        isDark = true
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                R.id.nav_about ->
                    startActivity(
                        Intent(
                            this,
                            AboutActivity::class.java
                        )
                    )
                R.id.nav_settings ->
                    startActivity(
                        Intent(
                            this,
                            SettingFragment::class.java
                        )
                    )

                R.id.nav_logout ->
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )


            }
            true

        }
        setupActionBarWithNavController(findNavController(R.id.fragment_main1))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_main1, MyPreferenceFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_main1)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    class MyPreferenceFragment : PreferenceFragmentCompat() {
        private lateinit var nav_view: NavigationView
        private lateinit var drawer: DrawerLayout
        private lateinit var toggle: ActionBarDrawerToggle
        private lateinit var volumePreference: SeekBarPreference
        private lateinit var signature: EditTextPreference
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preference, rootKey)



            volumePreference = findPreference("volume_notifications")!!
            if (volumePreference != null && ::volumePreference.isInitialized) {
                volumePreference.setOnPreferenceChangeListener { preference, newValue ->
                        val volume = newValue as? Int
                        if (volume != null) {
                            // Xử lý sự thay đổi âm lượng ở đây
                            true
                            val audioManager =
                                requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
                            audioManager.setStreamVolume(
                                AudioManager.STREAM_NOTIFICATION,
                                volume,
                                0
                            )
                        }
                        true
                    }
                } else {
                    // Xử lý khi preference không tồn tại
                }
            signature= findPreference("signature")!!
            if (::signature.isInitialized) {
                signature?.setOnPreferenceChangeListener { preference, newValue ->
                    val newKeySignature = newValue as? String
                    if (newKeySignature != null) {
                        // Xử lý sự thay đổi tên key signature ở đây
                        // ...
                    }
                    true
                }
            } else {
                // Xử lý khi preference không tồn tại
            }
        }

            }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
        }


