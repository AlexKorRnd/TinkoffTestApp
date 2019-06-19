package com.example.refillpoints.domain.models

import android.os.Parcel
import android.os.Parcelable

data class RefillPointModel(
    val partner: PartnerModel,
    val addressInfo: String?,
    val externalId: String,
    val fullAddress: String,
    val location: LocationModel,
    val phones: String?,
    val workHours: String?
): Parcelable {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(PartnerModel::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(LocationModel::class.java.classLoader),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(partner, flags)
        parcel.writeString(addressInfo)
        parcel.writeString(externalId)
        parcel.writeString(fullAddress)
        parcel.writeParcelable(location, flags)
        parcel.writeString(phones)
        parcel.writeString(workHours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RefillPointModel> {
        override fun createFromParcel(parcel: Parcel): RefillPointModel {
            return RefillPointModel(parcel)
        }

        override fun newArray(size: Int): Array<RefillPointModel?> {
            return arrayOfNulls(size)
        }
    }

}