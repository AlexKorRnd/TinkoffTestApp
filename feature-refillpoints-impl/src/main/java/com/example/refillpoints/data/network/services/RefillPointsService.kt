package com.example.refillpoints.data.network.services

import com.example.core_network_api.data.BaseResponse
import com.example.refillpoints.data.network.responses.PartnerResponse
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RefillPointsService {

    @GET("v1/deposition_points")
    fun loadRefillPoints(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("radius") radius: Int
    ): Single<BaseResponse<List<RefillPointsResponse>>>

    @GET("v1/deposition_partners")
    fun loadPartners(
        @Query("accountType") accountType: String
    ): Single<BaseResponse<List<PartnerResponse>>>

}