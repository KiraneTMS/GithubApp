package com.arine.githubapp

import android.content.Context

object Injector {
    fun provideRespository(context: Context): Respository{
        val apiService = RetrofitInstance.getAPIClient()
        return Respository.getRespository(apiService)
    }
}