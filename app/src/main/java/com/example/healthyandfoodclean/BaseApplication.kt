package com.example.healthyandfoodclean

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
//cau hinh cho hoat dong chay
@HiltAndroidApp
class BaseApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}