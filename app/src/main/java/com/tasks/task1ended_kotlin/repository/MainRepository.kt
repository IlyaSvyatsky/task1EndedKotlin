package com.tasks.task1ended_kotlin.repository

import com.tasks.task1ended_kotlin.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getCoins() = apiHelper.getCoins()
    suspend fun getPrices() = apiHelper.getPrices()
}