package com.tasks.task1ended_kotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasks.task1ended_kotlin.model.Coin
import com.tasks.task1ended_kotlin.repository.MainRepository
import kotlinx.coroutines.*

class MainViewModelCoin constructor(private val repository: MainRepository) : ViewModel() {

    var coinList = MutableLiveData<List<Coin>>()

    val errorMessage = MutableLiveData<String>()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()


    fun getCoinsData(): MutableLiveData<List<Coin>> {
        coinList = MutableLiveData<List<Coin>>()
        return coinList
    }

    fun getCoins() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getCoins()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val coinResponse = response.body()
                    val mList = ArrayList<Coin>(coinResponse!!.getCoins())
                    coinList.postValue(mList)

                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    /*fun getAllMovies() {

  val response = repository.getAllMovies()
  response.enqueue(object : Callback<CoinResponse> {
      override fun onResponse(call: Call<CoinResponse>, response: Response<CoinResponse>) {
          var coinResponse = response.body()
          var mList = ArrayList<Movie>(coinResponse!!.getCoins())
          movieList.postValue(mList)
      }

      override fun onFailure(call: Call<CoinResponse>, t: Throwable) {
          errorMessage.postValue(t.message)
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