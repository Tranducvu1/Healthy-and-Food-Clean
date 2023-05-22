package com.example.healthyandfoodclean.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.other.Constants
import com.example.healthyandfoodclean.other.Constants.Companion.ACTION_PAUSE_SERVICE
import com.example.healthyandfoodclean.other.Constants.Companion.ACTION_START_OR_RESUME_SERVICE
import com.example.healthyandfoodclean.other.Constants.Companion.ACTION_STOP_SERVICE
import com.example.healthyandfoodclean.other.Constants.Companion.FASTEST_LOCATION_UPDATE_INTERVAL
import com.example.healthyandfoodclean.other.Constants.Companion.LOCATION_UPDATE_INTERVAL
import com.example.healthyandfoodclean.other.Constants.Companion.NOTIFICATION_CHANNEL_ID
import com.example.healthyandfoodclean.other.Constants.Companion.NOTIFICATION_CHANNEL_NAME
import com.example.healthyandfoodclean.other.Constants.Companion.NOTIFICATION_ID
import com.example.healthyandfoodclean.other.TrackingUtility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>

//cau hinh lifecycle
@AndroidEntryPoint
class TrackingService : LifecycleService() {

    private val timeRunInSeconds = MutableLiveData<Long>()

    private var isFirstRun = true
    private var serviceKilled = false

    companion object {
        val timeRunInMillis = MutableLiveData<Long>()
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<Polylines>()
    }

    /**
     * Base notification builder that contains the settings every notification will have
     */
    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    /**
     * Builder of the current notification
     */
    private lateinit var curNotification: NotificationCompat.Builder

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        curNotification = baseNotificationBuilder
        postInitialValues()

        isTracking.observe(this) {
            updateNotificationTrackingState(it)
            updateLocationChecking(it)
        }
    }
//dat gia tri cho diem , time
    private fun postInitialValues() {
        timeRunInMillis.postValue(0L)
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
        timeRunInSeconds.postValue(0L)
    }
//khoi tao hoat dong khi xu ly click
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                        serviceKilled = false
                    } else {
                        startTimer()
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused Service")
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service.")
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

 //dung dich vu
    private fun killService() {
        serviceKilled = true
        isFirstRun = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }

   //Bật hoặc tắt theo dõi vị trí theo trạng thái theo dõi
    @SuppressLint("MissingPermission")
    private fun updateLocationChecking(isTracking: Boolean) {
        if (isTracking) {
            if (TrackingUtility.hasLocationPermissions(this)) {
                val request = com.google.android.gms.location.LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_UPDATE_INTERVAL
                    priority =
                        com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

   //Gọi lại vị trí nhận cập nhật vị trí và thêm chúng vào pathPoints.
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            if(isTracking.value!!) {
                result?.locations?.let { locations ->
                    for(location in locations) {
                        addPathPoint(location)
                    }
                }
            }
        }
    }

    private var isTimerEnabled = false
    private var lapTime = 0L // thời gian kể từ khi chúng tôi bắt đầu hẹn giờ
    private var timeRun = 0L // tổng thời gian của timer
    private var timeStarted = 0L // thời gian khi chúng tôi bắt đầu hẹn giờ
    private var lastSecondTimestamp = 0L

  //Bắt đầu hẹn giờ để theo dõi.
    private fun startTimer() {
        addEmptyPolyline()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!) {
                // time difference between now and time started
                lapTime = System.currentTimeMillis() - timeStarted
                // post the new laptime
                timeRunInMillis.postValue(timeRun + lapTime)
                // if a new second was reached, we want to update timeRunInSeconds, too
                if (timeRunInMillis.value!! >= lastSecondTimestamp + 1000L) {
                    timeRunInSeconds.postValue(timeRunInSeconds.value!! + 1)
                    lastSecondTimestamp += 1000L
                }
                delay(Constants.TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }

//Vô hiệu hóa bộ đếm thời gian và theo dõi.
    private fun pauseService() {
        isTimerEnabled = false
        isTracking.postValue(false)
    }

 // Thao tác này sẽ thêm vị trí vào danh sách các điểm đường dẫn cuối cùng.
    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply {
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }


// Sẽ thêm một đa tuyến trống trong danh sách pathPoints hoặc khởi tạo nó nếu trống.
    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))
// Bắt đầu dịch vụ này dưới dạng dịch vụ tiền cảnh và tạo thông báo cần thiết
    private fun startForegroundService() {
        Timber.d("TrackingService started.")

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, curNotification.build())
        //curNotification = curNotification.setContentIntent(getActivityPendingIntent())
        startTimer()
        isTracking.postValue(true)

    //cập nhật thông báo
        timeRunInSeconds.observe(this) {
            if(!serviceKilled) {
                val notification = curNotification
                    .setContentText(TrackingUtility.getFormattedStopWatchTime(it * 1000L))
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
        }
    }

   //Cập nhật các nút hành động của thông báo
    private fun updateNotificationTrackingState(isTracking: Boolean) {
        val notificationActionText = if (isTracking) "Pause" else "Resume"
        val pendingIntent = if (isTracking) {
            val pauseIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_PAUSE_SERVICE
            }
            PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            val resumeIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_START_OR_RESUME_SERVICE
            }
            PendingIntent.getService(this, 2, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        curNotification.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(curNotification, ArrayList<NotificationCompat.Action>())
        }

        if(!serviceKilled) {
            curNotification = baseNotificationBuilder
                .addAction(R.drawable.ic_pause_black_24dp, notificationActionText, pendingIntent)
            notificationManager.notify(NOTIFICATION_ID, curNotification.build())
        }
    }
//tao kenh thong bao hien thi
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

}

