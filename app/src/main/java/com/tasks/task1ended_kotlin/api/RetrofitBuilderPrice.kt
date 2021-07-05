package com.tasks.task1ended_kotlin.api

import com.tasks.task1ended_kotlin.model.Coin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilderPrice constructor(coin: Coin) {

    var mCoin: Coin = coin

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coincap.io/v2/assets/" + mCoin.id + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
