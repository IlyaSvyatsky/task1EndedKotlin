package com.tasks.task1ended_kotlin.api

import com.tasks.task1ended_kotlin.response.CoinResponse
import com.tasks.task1ended_kotlin.response.PriceResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("assets")
    suspend fun getCoins() : Response<CoinResponse>

    @GET("history?interval=d1")
    suspend fun getPrices(): Response<PriceResponse>
}