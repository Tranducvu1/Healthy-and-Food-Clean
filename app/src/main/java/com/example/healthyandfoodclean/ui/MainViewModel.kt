package com.example.healthyandfoodclean.ui


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel


import com.example.healthyandfoodclean.repositories.MainRepository


class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
): ViewModel() {
}
