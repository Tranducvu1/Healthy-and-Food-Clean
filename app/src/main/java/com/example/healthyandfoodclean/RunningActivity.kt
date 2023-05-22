package com.example.healthyandfoodclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


import com.example.healthyandfoodclean.other.Constants
import com.example.healthyandfoodclean.other.TrackingUtility
import com.example.healthyandfoodclean.services.TrackingService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.button.MaterialButton


import androidx.lifecycle.Observer
import com.example.healthyandfoodclean.fragment.ListenerActivity
import com.google.android.material.textview.MaterialTextView
import javax.inject.Inject


class RunningActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var btnFinishRun: MaterialButton
    private lateinit var tvLetsGo:MaterialTextView
    private lateinit var  btnToggleRun: MaterialButton
    private lateinit var tvTimer:MaterialTextView
    @set:Inject
    var weight: Float = 80f

    private var map: GoogleMap? = null
    private var isTracking = false
    private var curTimeInMillis = 0L
    private var pathPoints = mutableListOf<MutableList<LatLng>>()

    // private val viewModel: MainViewModel by viewModels()

    //  private var menu: Menu? = null
//crate view and return view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)

        mapView = findViewById(R.id.mapView)
        btnToggleRun  = findViewById(R.id.btnToggleRun)
        btnFinishRun = findViewById(R.id.btnFinishRun)
        tvTimer = findViewById(R.id.tvTimer)
        mapView.onCreate(savedInstanceState)
        btnToggleRun.setOnClickListener {
            toggleRun()
        }
        btnFinishRun.setOnClickListener {
            startActivity(
                Intent(
                    this,
                   HomeActivity::class.java
                )
            )
        }
        //get gia tri gg map
        mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObservers()
    }
//follow value run
    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(this, Observer {
            updateTracking(it)
        })
//theo doi danh sach diem (kinh do, vi do)
        TrackingService.pathPoints.observe(this, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
//theo dõi danh sách chạy
        TrackingService.timeRunInMillis.observe(this, Observer {
            curTimeInMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis, true)
            tvTimer.text = formattedTime
        })
    }
//kiem tra khi nhan nut stop
    private fun toggleRun() {
        if(isTracking) {
            sendCommandToService(Constants.ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if(!isTracking) {
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = "Stop"
            btnFinishRun.visibility = View.GONE
        }
    }
//di chuyen camera thong qua diem dau va cuoi
    private fun moveCameraToUser() {
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    Constants.MAP_ZOOM
                )
            )
        }
    }
//them
    private fun addAllPolylines() {
        for(polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(Constants.POLYLINE_COLOR)
                .width(Constants.POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }
//them gia tri tuyen duong dua tren diem cuoi gg map
    private fun addLatestPolyline() {
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(Constants.POLYLINE_COLOR)
                .width(Constants.POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(this, TrackingService::class.java).also {
            it.action = action
            startService(it)
        }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
//kiem tra hoat dong gg map
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}




