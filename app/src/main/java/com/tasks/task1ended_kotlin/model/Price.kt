package com.tasks.task1ended_kotlin.model

import android.os.Parcel
import android.os.Parcelable

data class Price(

    var priceUsd: String? = null,
    var time: String? = null,

    ): Parcelable {

    constructor(parcel: Parcel) : this() {
        priceUsd = parcel.readString()
        time = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(priceUsd)
        dest.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Price> {
        override fun createFromParcel(parcel: Parcel): Price {
            return Price(parcel)
        }

        override fun newArray(size: Int): Array<Price?> {
            return arrayOfNulls(size)
        }
    }
}