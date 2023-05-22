package com.example.healthyandfoodclean.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//cretae database run
@Database(
    entities = [Run::class],
    version = 2
)
//change class convert
@TypeConverters(Converters::class)
abstract class RunningDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDao
}