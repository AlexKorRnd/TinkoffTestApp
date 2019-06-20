package com.example.refillpoints.data

import com.example.core.di.PerFeature
import com.example.core_network_api.data.HttpClientApi
import com.example.refillpoints.data.db.DatabaseHolder
import com.example.refillpoints.data.db.mappings.toEntity
import com.example.refillpoints.data.db.mappings.toModel
import com.example.refillpoints.data.db.models.PartnerEntity
import com.example.refillpoints.data.db.models.RefillPointSeenEntity
import com.example.refillpoints.data.network.responses.PartnerResponse
import com.example.refillpoints.data.network.responses.RefillPointsResponse
import com.example.refillpoints.data.network.services.RefillPointsService
import com.example.refillpoints.domain.RefillPointsRepository
import com.example.refillpoints.domain.models.RefillPointModel
import io.reactivex.Single
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@PerFeature
class RefillPointsRepositoryImpl @Inject constructor(
    private val httpClient: HttpClientApi
) : RefillPointsRepository {

    companion object {
        private const val DEFAULT_ACCOUNT_TYPE = "Credit"
    }

    private val refillService = httpClient.provideClient().create(RefillPointsService::class.java)

    private fun loadPartners(): Single<List<PartnerEntity>> {
        return Single.fromCallable {
            val query = DatabaseHolder.get()?.partnerDao()?.queryForAll()
            query
        }.flatMap {
            Timber.i("loadPartnersFromDB:: count in DB: ${it.size}")
            if (it.isEmpty()) {
                loadPartnersFromNetwork()
                    .map { response ->
                        savePartnersToDB(response)
                    }
            } else {
                Single.just(it)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun savePartnersToDB(partners: List<PartnerResponse>): List<PartnerEntity> {
        val helper = DatabaseHolder.get()
        return partners.map { partnerResponse ->
            val partnerEntity = partnerResponse.toEntity()
            partnerResponse.limits.forEach {
                helper.currencyDao()?.createOrUpdate(it.currency.toEntity())
                helper.limitDao()?.createOrUpdate(it.toEntity(partnerEntity))
            }
            partnerResponse.dailyLimits.forEach {
                helper.currencyDao()?.createOrUpdate(it.currency.toEntity())
                helper.dailyLimitDao()?.createOrUpdate(it.toEntity(partnerEntity))
            }
            helper.partnerDao()?.createOrUpdate(partnerEntity)
            partnerEntity
        }
    }

    private fun loadPartnersFromNetwork(): Single<List<PartnerResponse>> {
        Timber.i("loadPartnersFromNetwork")
        return refillService.loadPartners(DEFAULT_ACCOUNT_TYPE)
            .subscribeOn(Schedulers.io())
            .compose(httpClient.provideResponseValidator().networkTransformer())
    }

    private fun saveRefillPoints(refillPoints: List<RefillPointsResponse>, partners: List<PartnerEntity>) {
        val helper = DatabaseHolder.get()
        refillPoints.forEach { refillPointResponse ->
            val locationEntity = refillPointResponse.location.toEntity()
            helper.locationDao()?.createOrUpdate(locationEntity)
            val pointEntity = refillPointResponse.toEntity(
                partnerEntity = partners.find { it.id == refillPointResponse.partnerName },
                locationEntity = locationEntity
            )
            helper.refillPointsDao()?.createOrUpdate(pointEntity)
        }
    }


    private fun loadRefillPointsFromNetwork(lat: Double, lng: Double, radius: Int): Single<List<RefillPointsResponse>> {
        return refillService.loadRefillPoints(lat, lng, radius)
            .compose(httpClient.provideResponseValidator().networkTransformer())
    }

    private fun loadSeenPoints(): Single<List<RefillPointSeenEntity>> {
        return Single.fromCallable {
            DatabaseHolder.get().seenPointsDao()?.queryForAll()?.toList()
        }
    }

    override fun loadRefillPoints(lat: Double, lng: Double, radius: Int): Single<List<RefillPointModel>> {
        return Single.zip(
            loadRefillPointsFromNetwork(lat, lng, radius),
            loadPartners(),
            loadSeenPoints(),
            Function3 { refillPoints: List<RefillPointsResponse>, partners: List<PartnerEntity>, seenPoints: List<RefillPointSeenEntity> ->
                RefillPointsResult(refillPoints, partners, seenPoints)
            })

            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                saveRefillPoints(it.refillPoints, it.partners)
            }
            .map { result ->
                result.refillPoints.map { refillPointsResponse ->
                    val partner = result.partners.find { it.id == refillPointsResponse.partnerName }?.toModel()
                    val isSeen = result.seenPoints.find { it.pointId == refillPointsResponse.externalId } != null
                    refillPointsResponse.toModel(partner!!, isSeen)
                }
            }
    }



    override fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel> {
        return Single.fromCallable {
            DatabaseHolder.get().seenPointsDao()?.createOrUpdate(RefillPointSeenEntity(refillPointModel.externalId, refillPointModel.isSeen))
        }.map {
            refillPointModel
        }
    }

    private class RefillPointsResult(
        val refillPoints: List<RefillPointsResponse>,
        val partners: List<PartnerEntity>,
        val seenPoints: List<RefillPointSeenEntity>
    )
}