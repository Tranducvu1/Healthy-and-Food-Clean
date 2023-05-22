package com.example.healthyandfoodclean.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.healthyandfoodclean.other.Constants
import androidx.core.app.NotificationCompat
import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.RunningActivity
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    //truy cập dịch vụ vị trí của hệ thống.
    fun providesFusedLocationProviderClient(
        @ApplicationContext context: Context
    ) = FusedLocationProviderClient(context)

    @ServiceScoped
    @Provides
    //thông báo trong ứng dụng.
    fun provideBaseNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
        .setContentTitle("Running App")
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)

    @ServiceScoped
    @Provides
    // thực thi trong hành dônnj chạy trong tương lai
    fun provideActivityPendingIntent(
        @ApplicationContext context: Context
    ) =
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, RunningActivity::class.java).apply {
                action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
}