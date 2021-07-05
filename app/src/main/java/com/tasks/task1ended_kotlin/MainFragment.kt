package com.tasks.task1ended_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tasks.task1ended_kotlin.adapter.MainAdapter
import com.tasks.task1ended_kotlin.api.ApiHelper
import com.tasks.task1ended_kotlin.api.RetrofitBuilderCoin
import com.tasks.task1ended_kotlin.databinding.FragmentMainBinding
import com.tasks.task1ended_kotlin.model.Coin
import com.tasks.task1ended_kotlin.viewModel.MainViewModelCoin
import com.tasks.task1ended_kotlin.viewModelFactory.ViewModelFactoryCoin

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModelCoin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactoryCoin(ApiHelper(RetrofitBuilderCoin.apiService))).get(
            MainViewModelCoin::class.java)

        viewModel.coinList.observe(viewLifecycleOwner, Observer  { coins ->

            val adapter = MainAdapter(coins, CoinListener())
            binding.recyclerview.adapter = adapter
            adapter.setMovieList(coins)

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getCoins()
    }

    companion object {

        private class CoinListener : MainAdapter.OnCoinClickListener {

            override fun OnCoinClick(coins: Coin, view: View) {
                Navigation.findNavController(view).navigate(
                    MainFragmentDirections.actionVehiclesFragmentToVehicleDetailFragment(
                        coins
                    )
                )
            }

        }
    }
}