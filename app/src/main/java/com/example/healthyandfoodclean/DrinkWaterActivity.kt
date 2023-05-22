package com.example.healthyandfoodclean

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.healthyandfoodclean.fragment.AboutActivity
import com.example.healthyandfoodclean.fragment.ChatGPTActivity
import com.example.healthyandfoodclean.fragment.ListenerActivity
import com.example.healthyandfoodclean.fragment.SettingFragment
import com.google.android.material.navigation.NavigationView

class DrinkWaterActivity : AppCompatActivity() {
    private lateinit var textViewReminder: TextView
    private lateinit var buttonWater: Button
    private lateinit var textViewResult: TextView
    private lateinit var imageView: ImageView
    private var waterConsumed: Int = 0
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var nav_view : NavigationView
    private lateinit var textViewAdvice: TextView
    private var isAnimating = false
    private var isForward = true
    private val animationDelay: Long = 1000 // Thời gian trễ giữa các vòng lặp (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        var isDark = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_water)
        nav_view = findViewById(R.id.nav_view)
        drawer = findViewById(R.id.drawerLayout)
        imageView = findViewById(R.id.imageViewWater)
        var hasDrunkWater = false // Kiểm tra xem bạn đã uống nước hay chưa
        textViewAdvice = findViewById(R.id.textViewAdvice)
        textViewAdvice.isSelected = true // Enable chế độ chạy nhấp nháy
        textViewAdvice.setTextColor(Color.RED) // Đặt màu chữ ban đầu
        toggle = ActionBarDrawerToggle(this, drawer, R.string.open_nav, R.string.close_nav)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        startBlinkAnimation()
//kiem tra uong nuoc
        textViewReminder = findViewById(R.id.textViewReminder)
        buttonWater = findViewById(R.id.buttonWater)
        textViewResult = findViewById(R.id.textViewResult)

        buttonWater.setOnClickListener {
            if (hasDrunkWater) {
                imageView.setImageResource(R.drawable.khatnuoc) // Đặt hình ảnh khi đã uống nước
            } else {
                waterConsumed += 250 // Giả sử uống 250ml mỗi lần
                textViewResult.text = "Water consumed: $waterConsumed ml"
                imageView.setImageResource(R.drawable.nonuoc) // Đặt hình ảnh khi chưa uống nước
            }
        }

        startWaterReminder()
        nav_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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
                R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.nav_water -> startActivity(Intent(this, DrinkWaterActivity::class.java))
                R.id.nav_randomfood -> startActivity(Intent(this, EatClean::class.java))
                R.id.nav_chatgpt -> startActivity(Intent(this, ChatGPTActivity::class.java))
                R.id.nav_listening -> startActivity(Intent(this, ListenerActivity::class.java))
                R.id.nav_switch -> {
                    try {
                        if (window != null && isDark) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            isDark = false
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            isDark = true
                        }
                    } catch (e: Exception) {
                        // Xử lý ngoại lệ nếu có
                        isDark = true
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
                R.id.nav_about -> startActivity(Intent(this, AboutActivity::class.java))
                R.id.nav_settings -> startActivity(Intent(this, SettingFragment::class.java))
                R.id.nav_logout -> startActivity(Intent(this, LoginActivity::class.java))
            }
            true
        }
    }

    private fun startWaterReminder() {
        val reminderTimer = object : CountDownTimer(30 * 60 * 1000, 30 * 60 * 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Không làm gì trong quá trình đếm
            }

            override fun onFinish() {
                textViewReminder.visibility = View.VISIBLE
            }
        }

        reminderTimer.start()
    }
//kiem tra tao hieu ug
    private fun startBlinkAnimation() {
        isAnimating = true

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (isAnimating) {
                    if (isForward) {
                        textViewAdvice.setHorizontallyScrolling(true)
                        textViewAdvice.setSingleLine(true)
                        textViewAdvice.ellipsize = null // Loại bỏ thuộc tính ellipsize

                        // Chạy từ trái sang phải
                        textViewAdvice.setHorizontallyScrolling(true)
                        textViewAdvice.isSelected = true
                        textViewAdvice.requestFocus()
                        textViewAdvice.setTextColor(Color.RED) // Đặt màu chữ

                    } else {
                        textViewAdvice.setHorizontallyScrolling(false)
                        textViewAdvice.setSingleLine(false)
                        textViewAdvice.ellipsize = TextUtils.TruncateAt.END // Đặt thuộc tính ellipsize trở lại

                        // Chạy từ phải sang trái
                        textViewAdvice.setHorizontallyScrolling(false)
                        textViewAdvice.isSelected = false
                        textViewAdvice.clearFocus()
                        textViewAdvice.setTextColor(Color.BLUE) // Đặt màu chữ
                    }

                    isForward = !isForward

                    handler.postDelayed(this, animationDelay)
                }
            }
        }

        handler.post(runnable)
    }
//huy


    override fun onDestroy() {
        super.onDestroy()
        isAnimating = false
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
