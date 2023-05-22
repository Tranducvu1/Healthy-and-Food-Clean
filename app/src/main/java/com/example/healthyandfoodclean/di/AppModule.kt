package com.example.healthyandfoodclean.di



import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.healthyandfoodclean.data.RunDao
import com.example.healthyandfoodclean.data.RunningDatabase
import com.example.healthyandfoodclean.other.Constants.Companion.DATABASE_NAME
import com.example.healthyandfoodclean.other.Constants.Companion.KEY_FIRST_TIME_TOGGLE
import com.example.healthyandfoodclean.other.Constants.Companion.KEY_NAME
import com.example.healthyandfoodclean.other.Constants.Companion.KEY_WEIGHT
import com.example.healthyandfoodclean.other.Constants.Companion.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

//AppModule, cung cấp ứng dụng singletons rộng
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    //update database hoat dong
    fun provideAppDb(app: Application): RunningDatabase {
        return Room.databaseBuilder(app, RunningDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase): RunDao {
        return db.getRunDao()
    }

    @Singleton
    @Provides
    //một lưu trữ dùng để lưu trữ dữ liệu
    fun provideSharedPreferences(app: Application) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT, 80f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences) = sharedPreferences.getBoolean(
        KEY_FIRST_TIME_TOGGLE, true
    )


}