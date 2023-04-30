package com.arine.githubapp

class Respository constructor(private val ApiClient : Search) {

    companion object{
        @Volatile
        private var INSTANCE: Respository? = null

        fun getRespository(githubClient: Search):Respository{
            return INSTANCE ?: synchronized(this) {
                val instance= Respository(githubClient)
                INSTANCE = instance
                instance
            }
        }
    }
}