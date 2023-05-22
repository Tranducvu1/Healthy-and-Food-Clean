package com.example.healthyandfoodclean

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.healthyandfoodclean.fragment.AboutActivity
import com.example.healthyandfoodclean.fragment.ChatGPTActivity
import com.example.healthyandfoodclean.fragment.ListenerActivity
import com.example.healthyandfoodclean.fragment.SettingFragment
import com.google.android.material.navigation.NavigationView

class  HealthyArticlesActivity : AppCompatActivity() {
    private lateinit var buttonBack: Button
    private lateinit var drawer: DrawerLayout
    private lateinit var nav_view : NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    //tao mang thong tin
    private val healthy = arrayOf(
        arrayOf("Long walking ", "", "", "", "Click More", ""),
        arrayOf("Covid-19", "", "", "", "Click More", ""),
        arrayOf("Stop Smoking", "", "", "", "Click More", ""),
        arrayOf("Fever", "", "", "", "Click More", ""),
        arrayOf("Good nutrition", "", "", "", "Click More", ""),
        arrayOf("Drink water", "", "", "", "Click More", "")
    )
    //tao mang anh
    private val images = intArrayOf(
        R.drawable.health1,
        R.drawable.health2,
        R.drawable.health3,
        R.drawable.health4,
        R.drawable.health5,
        R.drawable.healthy6
    )
    private var itemList = ArrayList<HashMap<String, String>>()
    private lateinit var sa: SimpleAdapter
    private lateinit var lst: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthy_articles)
//        buttonBack = findViewById(R.id.buttonBack)
        lst = findViewById(R.id.listview)
        var isDark = false
        drawer = findViewById(R.id.drawlayout)
        nav_view = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.open_nav, R.string.close_nav)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        buttonBack.setOnClickListener {
//            startActivity(Intent(this, HomeActivity::class.java))
//        }
//tao mang luu du lieu
        //thuc thi anh xa du lieu
        for (i in healthy.indices) {
            val item = HashMap<String, String>()
            item["Line1"] = healthy[i][0]
            item["Line2"] = healthy[i][1]
            item["Line3"] = healthy[i][2]
            item["Line4"] = healthy[i][3]
            item["Line5"] = healthy[i][4]
            item["Line6"] = healthy[i][5]
            itemList.add(item)
        }

        sa = SimpleAdapter(
            this,
            itemList,
            android.R.layout.simple_list_item_1,
            arrayOf("Line1", "Line2", "Line3", "Line4", "Line5", "Line6"),
            intArrayOf(
                android.R.id.text1,
                android.R.id.text2,

                )
        )
        //chuyen sang muc duoc chon
        //anh xa khi click
        lst.adapter = sa
        lst.setOnItemClickListener { adapterView, view, i, l ->
            val it = Intent(this, HealthyArticleDetailActivity::class.java)
            it.putExtra("text1", healthy[i][0])
            it.putExtra("text2", images[i])
            startActivity(it)
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
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}