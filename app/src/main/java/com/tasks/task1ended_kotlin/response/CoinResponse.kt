package com.tasks.task1ended_kotlin.response

import com.tasks.task1ended_kotlin.model.Coin

data class CoinResponse(


    private var data: List<Coin>
){
    fun getCoins(): List<Coin> {
        return data
    }

    fun setCoins(data: List<Coin>) {
        this.data = data
    }
}