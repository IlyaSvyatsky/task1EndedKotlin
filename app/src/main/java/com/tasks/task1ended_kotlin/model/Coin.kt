package com.tasks.task1ended_kotlin.model

import android.os.Parcel
import android.os.Parcelable

data class Coin(

    var id: String? = null,
    var rank: String? = null,
    var symbol: String? = null,
    var name: String? = null,
    var supply: String? = null,
    var maxSupply: String? = null,
    var marketCapUsd: String? = null,
    var volumeUsd24Hr: String? = null,
    var priceUsd: Double? = null,
    var changePercent24Hr: Double? = null,
    var vwap24Hr: String? = null,
    var explorer: String? = null

): Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        rank = parcel.readString()
        symbol = parcel.readString()
        name = parcel.readString()
        supply = parcel.readString()
        maxSupply = parcel.readString()
        marketCapUsd = parcel.readString()
        volumeUsd24Hr = parcel.readString()
        priceUsd = parcel.readDouble()
        changePercent24Hr = parcel.readDouble()
        vwap24Hr = parcel.readString()
        explorer = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeString(symbol)
        dest.writeString(rank)
        dest.writeString(priceUsd.toString())
        dest.writeString(marketCapUsd)
        dest.writeString(supply)
        dest.writeString(maxSupply)
        dest.writeString(volumeUsd24Hr)
        dest.writeString(changePercent24Hr.toString())
        dest.writeString(vwap24Hr)
        dest.writeString(explorer)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }
}