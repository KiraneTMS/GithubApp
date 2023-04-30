package com.arine.githubapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arine.githubapp.viewmodels.DetailViewModel

class DetailViewModelFactory(private val respository: Respository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(respository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}