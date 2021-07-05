package com.tasks.task1ended_kotlin.response

import com.tasks.task1ended_kotlin.model.Price

data class PriceResponse(


    var data: List<Price>
){
    fun getPrice(): List<Price> {
        return data
    }

    fun setPrice(data: List<Price>) {
        this.data = data
    }
}