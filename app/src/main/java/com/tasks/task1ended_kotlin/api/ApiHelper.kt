package com.tasks.task1ended_kotlin.api

// класс, который поможет с вызовом ApiService

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCoins() = apiService.getCoins()
    suspend fun getPrices() = apiService.getPrices()
}