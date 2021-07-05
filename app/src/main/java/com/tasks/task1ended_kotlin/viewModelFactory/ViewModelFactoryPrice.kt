package com.tasks.task1ended_kotlin.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tasks.task1ended_kotlin.api.ApiHelper
import com.tasks.task1ended_kotlin.model.Coin
import com.tasks.task1ended_kotlin.repository.MainRepository
import com.tasks.task1ended_kotlin.viewModel.MainViewModelPrice

class ViewModelFactoryPrice constructor(application: Application, coin: Coin, private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    private val mApplication: Application = application
    private var mCoin: Coin = coin

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModelPrice::class.java)) {
            MainViewModelPrice(mApplication, mCoin, MainRepository(apiHelper)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
