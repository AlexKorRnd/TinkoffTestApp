package com.example.refillpoints.domain.models

import android.os.Parcel
import android.os.Parcelable

data class PartnerModel(
    val id: String,
    val name: String,
    val picture: String,
    val url: String?,
    val depositionDuration: String,
    val limitations: String,
    val description: String
) : Parcelable {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(picture)
        parcel.writeString(url)
        parcel.writeString(depositionDuration)
        parcel.writeString(limitations)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PartnerModel> {
        override fun createFromParcel(parcel: Parcel): PartnerModel {
            return PartnerModel(parcel)
        }

        override fun newArray(size: Int): Array<PartnerModel?> {
            return arrayOfNulls(size)
        }

        const val PICTURE_URL_PREFIX = "https://static.tinkoff.ru/icons/deposition-partners-v3"
    }

}