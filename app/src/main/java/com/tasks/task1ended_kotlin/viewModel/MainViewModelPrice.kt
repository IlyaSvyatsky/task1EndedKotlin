package com.tasks.task1ended_kotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tasks.task1ended_kotlin.model.Coin
import com.tasks.task1ended_kotlin.model.Price
import com.tasks.task1ended_kotlin.repository.MainRepository
import kotlinx.coroutines.*

class MainViewModelPrice(application: Application, coin: Coin, private val repository: MainRepository) :
    AndroidViewModel(application) {

    private val detailCoinList: MutableLiveData<Coin> = MutableLiveData<Coin>()

    var priceList = MutableLiveData<List<Price>>()

    val errorMessage = MutableLiveData<String>()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    init {
        detailCoinList.postValue(coin)
    }

    fun getSelectedCoinList(): MutableLiveData<Coin> {
        return detailCoinList
    }

    fun getPrices(): MutableLiveData<List<Price>> {
        priceList = MutableLiveData<List<Price>>()
        return priceList
    }


    fun loadPrices() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getPrices()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val priceResponse = response.body()
                    val pList = ArrayList<Price>(priceResponse!!.getPrice())
                    priceList.postValue(pList)

                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    //fun getPrices() {
    /*fun loadPrices() {
        val response = repository.getPrices()
        response.enqueue(object : Callback<PriceResponse> {
            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                val priceResponse = response.body()
                //var pList = ArrayList<Price>(priceResponse!!.getPrice())
                val pList = priceResponse?.getPrice()
                //priceList = response.body()
                priceList.postValue(pList!!)
            }
        })
    }*/

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}