package com.example.healthyandfoodclean.repositories

import com.example.healthyandfoodclean.data.Run
import com.example.healthyandfoodclean.data.RunDao
import javax.inject.Inject

    class MainRepository @Inject constructor(
        val runDao: RunDao
    )