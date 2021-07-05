package com.tasks.task1ended_kotlin

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.tasks.task1ended_kotlin.api.ApiHelper
import com.tasks.task1ended_kotlin.api.RetrofitBuilderPrice
import com.tasks.task1ended_kotlin.databinding.FragmentDetailBinding
import com.tasks.task1ended_kotlin.model.Coin
import com.tasks.task1ended_kotlin.viewModel.MainViewModelPrice
import com.tasks.task1ended_kotlin.viewModelFactory.ViewModelFactoryPrice

class DetailFragment : Fragment() {

    private var viewModel: MainViewModelPrice? = null
    var graphView: GraphView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = requireActivity().application
        val coin: Coin = DetailFragmentArgs.fromBundle(requireArguments()).detailFragmentArgs
        val retrofitBuilder = RetrofitBuilderPrice(coin)
        val factory = ViewModelFactoryPrice(application, coin, ApiHelper(retrofitBuilder.apiService))
        viewModel = ViewModelProvider(this, factory).get(MainViewModelPrice::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_detail, container, false)

        var mTextName = view.findViewById<TextView>(R.id.text_name)
        var mTextSymbol = view.findViewById<TextView>(R.id.text_symbol)
        var mTextPriceUSD = view.findViewById<TextView>(R.id.coin_price)
        var mTextAveragePrice = view.findViewById<TextView>(R.id.text_average_price)
        var mTextPercentChanged = view.findViewById<TextView>(R.id.coin_percent_change)
        var mTextAvailableSupply = view.findViewById<TextView>(R.id.text_available_supply)
        var mTextMaximumSupply = view.findViewById<TextView>(R.id.text_max_supply)
        var mTextMarketCapUSD = view.findViewById<TextView>(R.id.text_market_cap)
        var mTextValumeUSD24Hr = view.findViewById<TextView>(R.id.text_volume)


        viewModel?.getSelectedCoinList()?.observe(viewLifecycleOwner,
            { coin ->
                mTextName.text = coin.name
                mTextSymbol.text = coin.symbol
                mTextPriceUSD.text = getString(R.string.coin_price, coin.priceUsd)
                mTextAveragePrice.text = getString(R.string.text_average_price, coin.vwap24Hr?.toDouble())
                mTextPercentChanged.text = getString(R.string.coin_percent_change, coin.changePercent24Hr)
                mTextAvailableSupply.text = getString(R.string.text_available_supply, coin.supply?.toDouble())
                mTextMaximumSupply.text = getString(R.string.text_max_supply, coin.maxSupply?.toDouble())
                mTextMarketCapUSD.text = getString(R.string.text_market_cap, coin.marketCapUsd?.toDouble())
                mTextValumeUSD24Hr.text = getString(R.string.text_volume, coin.volumeUsd24Hr ?.toDouble())

                if ( coin.changePercent24Hr!! >= 0) {
                    mTextPercentChanged.setTextColor(
                        resources.getColor(R.color.percentChangePositive)
                    )
                } else {
                    mTextPercentChanged.setTextColor(
                        resources.getColor(R.color.percentChangeNegative)
                    )
                }

            })

        graphView = view.findViewById<View>(R.id.graph) as GraphView

        viewModel?.loadPrices()

        viewModel?.priceList?.observe(viewLifecycleOwner, Observer  { pList ->

            val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()

            for (i in pList.indices) {

                val y = pList[i].priceUsd

                if (y != null) {
                    series.appendData(DataPoint(i.toDouble(), y.toDouble()), true, pList.size)
                }
            }

            graphView?.addSeries(series)
            series.color = Color.rgb(147, 112, 219)
            series.title = "Price Curve 1"
            series.thickness = 5
            graphView?.title = "Price Graph"
            graphView?.titleTextSize = 50f
            graphView?.titleColor = Color.rgb(255, 255, 255)
            graphView?.legendRenderer?.isVisible = true
            graphView?.legendRenderer?.align = LegendRenderer.LegendAlign.TOP
            graphView?.legendRenderer?.textColor = Color.rgb(255, 255, 255)
            val gridLabelRenderer = graphView?.gridLabelRenderer
            gridLabelRenderer?.horizontalAxisTitle = "X Axis Title"
            gridLabelRenderer?.horizontalAxisTitleTextSize = 30f
            gridLabelRenderer?.verticalAxisTitle = "Y Axis Title"
            gridLabelRenderer?.verticalAxisTitleTextSize = 30f
            gridLabelRenderer?.gridColor = Color.rgb(255, 255, 255)
            gridLabelRenderer?.horizontalAxisTitleColor = Color.rgb(
                147,
                112,
                219
            )
            gridLabelRenderer?.verticalAxisTitleColor = Color.rgb(
                147,
                112,
                219
            )
            gridLabelRenderer?.verticalLabelsColor = Color.rgb(255, 255, 255)
            gridLabelRenderer?.horizontalLabelsColor = Color.rgb(
                255,
                255,
                255
            )
        })
        return view
    }
}