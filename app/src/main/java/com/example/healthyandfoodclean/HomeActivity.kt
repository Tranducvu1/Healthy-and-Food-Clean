package com.example.healthyandfoodclean
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.healthyandfoodclean.databinding.ActivityHomeBinding
import com.example.healthyandfoodclean.fragment.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HomeActivity : AppCompatActivity() {
    //hien thi thanh dieu huong
    private lateinit var drawer: DrawerLayout
    private lateinit var nav_view : NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    //giao dien chinh
    private lateinit var binding: ActivityHomeBinding
    private lateinit var tvname : TextView
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorSecondaryLight)))
        tvname = binding.navView.getHeaderView(0).findViewById(R.id.tvname)
        database = FirebaseDatabase.getInstance().getReference()
        var isDark = false
        drawer = findViewById(R.id.drawlayout)
        nav_view = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.open_nav, R.string.close_nav)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        database = FirebaseDatabase.getInstance().getReference()
       val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("User").child(userId).get().addOnSuccessListener {
            val name = it.child("name").value?.toString() ?: "User"
            tvname.text = name
            val email = it.child("email").value.toString()
        }.addOnFailureListener{
           Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        }
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
        binding.healthyarticles.setOnClickListener {
            startActivity(
                Intent(
                    this, HealthyArticlesActivity::class.java

                )
            )
        }
        binding.doexercise.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ExcerciseActivitty::class.java

                )
            )
        }
        binding.runner.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RunningActivity::class.java

                )
            )
        }

        binding.cleanfood.setOnClickListener {
            startActivity(
                Intent(
                    this, SplashActivity::class.java

                )
            )
        }
    }
//hien thi bang chon
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}