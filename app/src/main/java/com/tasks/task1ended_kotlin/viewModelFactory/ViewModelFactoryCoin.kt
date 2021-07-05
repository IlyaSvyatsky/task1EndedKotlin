package com.tasks.task1ended_kotlin.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tasks.task1ended_kotlin.api.ApiHelper
import com.tasks.task1ended_kotlin.repository.MainRepository
import com.tasks.task1ended_kotlin.viewModel.MainViewModelCoin

class ViewModelFactoryCoin constructor(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModelCoin::class.java)) {
            MainViewModelCoin(MainRepository((apiHelper))) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}