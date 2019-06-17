package com.example.refillpoints.data

import com.example.core.di.PerFeature
import com.example.core_network_api.data.HttpClientApi
import com.example.refillpoints.data.network.responses.PartnerResponse
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import com.example.refillpoints.data.network.services.RefillPointsService
import com.example.refillpoints.domain.RefillPointsRepository
import io.reactivex.Single
import javax.inject.Inject

@PerFeature
class RefillPointsRepositoryImpl @Inject constructor(
    private val httpClient: HttpClientApi
): RefillPointsRepository {

    private val refillService = httpClient.provideClient().create(RefillPointsService::class.java)

    override fun loadPartners(): Single<List<PartnerResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointsResponse>> {
        return refillService.loadRefillPoints(lat, lng, radius)
            .compose(httpClient.provideResponseValidator().networkTransformer())
    }
}