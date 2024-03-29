package com.example.refillpoints.domain.models

import android.os.Parcel
import android.os.Parcelable

data class LocationModel(
        val latitude: Double,
        val longitude: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationModel> {
        override fun createFromParcel(parcel: Parcel): LocationModel {
            return LocationModel(parcel)
        }

        override fun newArray(size: Int): Array<LocationModel?> {
            return arrayOfNulls(size)
        }
    }


}