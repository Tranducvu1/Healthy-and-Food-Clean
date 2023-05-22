package com.example.healthyandfoodclean

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.healthyandfoodclean.fragment.AboutActivity
import com.example.healthyandfoodclean.fragment.ChatGPTActivity
import com.example.healthyandfoodclean.fragment.ListenerActivity
import com.example.healthyandfoodclean.fragment.SettingFragment
import com.google.android.material.navigation.NavigationView

class ExcerciseActivitty : AppCompatActivity() {
    private lateinit var drawer: DrawerLayout
    private lateinit var nav_view: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_activitty)

        var isDark = false
        drawer = findViewById(R.id.drawlayout)
        nav_view = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.open_nav, R.string.close_nav)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val button1 = findViewById<Button>(R.id.start)
        val button2 = findViewById<Button>(R.id.start2)

        button1.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SecondActivity::class.java
                )
            )
        }
        button2.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SecondActivity2::class.java
                )
            )
        }
        nav_view.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_notification -> {
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
                R.id.nav_switch ->
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

        fun beforeage18(view: View) {

            startActivity(
                Intent(
                    this,
                    SecondActivity::class.java
                )
            )
        }

        fun age18(view: View) {
            startActivity(
                Intent(
                    this,
                    SecondActivity::class.java
                )
            )
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}