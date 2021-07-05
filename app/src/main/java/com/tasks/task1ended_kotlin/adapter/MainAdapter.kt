package com.tasks.task1ended_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tasks.task1ended_kotlin.R
import com.tasks.task1ended_kotlin.model.Coin

class MainAdapter (/*private var movies: MutableList<Movie> = mutableListOf<Movie>(),*/
    private var coins: List<Coin> = mutableListOf<Coin>(),
    // private var movies: ArrayList<Movie>,
    var listener: OnCoinClickListener
): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    var context: Context? = null

    fun setMovieList(movies: List<Coin>) {
        this.coins = movies.toMutableList()
        notifyDataSetChanged()
    }

    interface OnCoinClickListener {
        fun OnCoinClick(coins: Coin, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View
        val layoutInflater = LayoutInflater.from(parent.context)
        view = layoutInflater.inflate(R.layout.item_coin, parent, false)

        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val coin = coins[position]

        holder.textViewId.text = coin.rank
        holder.textViewName.text = coin.name
        holder.textViewSymbol.text = coin.symbol
        holder.textViewPrice.text = coin.priceUsd.toString()
        holder.textViewPercentChange.text = coin.changePercent24Hr.toString()

        holder.textViewPrice.text = context!!.getString(R.string.coin_price, coin.priceUsd)
        holder.textViewPercentChange.text = context!!.getString(R.string.coin_percent_change, coin.changePercent24Hr)

        if ( coin.changePercent24Hr!! >= 0) {
            holder.textViewPercentChange.setTextColor(
                context!!.resources.getColor(R.color.percentChangePositive)
            )
        } else {
            holder.textViewPercentChange.setTextColor(
                context!!.resources.getColor(R.color.percentChangeNegative)
            )
        }
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    inner class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val textViewId: TextView
        val textViewName: TextView
        val textViewSymbol: TextView
        val textViewPrice: TextView
        val textViewPercentChange: TextView

        init {
            context = itemView.context
            textViewId = itemView.findViewById(R.id.coin_id)
            textViewName = itemView.findViewById(R.id.coin_name)
            textViewPrice = itemView.findViewById(R.id.coin_price)
            textViewSymbol = itemView.findViewById(R.id.coin_symbol)
            textViewPercentChange = itemView.findViewById(R.id.coin_percent_change)

            itemView.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    listener.OnCoinClick(coins[adapterPosition], itemView)
                }
            });
        }
    }
}