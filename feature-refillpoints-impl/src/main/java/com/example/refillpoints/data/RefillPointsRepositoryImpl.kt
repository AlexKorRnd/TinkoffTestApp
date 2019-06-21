package com.example.refillpoints.data

import com.example.core.di.PerFeature
import com.example.core_network_api.data.HttpClientApi
import com.example.refillpoints.data.db.DatabaseHolder
import com.example.refillpoints.data.db.models.PartnerEntity
import com.example.refillpoints.data.db.models.PointsInAreaEntities
import com.example.refillpoints.data.db.models.RefillPointSeenEntity
import com.example.refillpoints.data.mappings.toEntity
import com.example.refillpoints.data.mappings.toModel
import com.example.refillpoints.data.network.responses.PartnerResponse
import com.example.refillpoints.data.network.services.RefillPointsService
import com.example.refillpoints.domain.RefillPointsRepository
import com.example.refillpoints.domain.models.LocationModel
import com.example.refillpoints.domain.models.RefillPointModel
import com.example.refillpoints.domain.models.ScreenRect
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerFeature
class RefillPointsRepositoryImpl @Inject constructor(
    private val httpClient: HttpClientApi
) : RefillPointsRepository {

    companion object {
        private const val DEFAULT_ACCOUNT_TYPE = "Credit"

        private const val VALID_CACHE_MINUTES = 10
    }

    private val refillService = httpClient.provideClient().create(RefillPointsService::class.java)

    override fun loadRefillPoints(centerPoint: LocationModel, screenRect: ScreenRect): Single<List<RefillPointModel>> {
        return loadPartners()
            .flatMap {
                internalLoadRefillPoints(centerPoint, screenRect)
            }
    }

    private fun internalLoadRefillPoints(
        centerPoint: LocationModel,
        screenRect: ScreenRect
    ): Single<List<RefillPointModel>> {
        return Single.fromCallable {
            DatabaseHolder.get().pointsAreaDao()?.queryForAll() ?: emptyList<PointsInAreaEntities>()
        }.flatMap {
            val cachedItem = containsRect(it, screenRect)
            val now = Timestamp(System.currentTimeMillis())
            val minutesBetween = TimeUnit.MILLISECONDS.toMinutes((now.time - (cachedItem?.timestamp?.time ?: 0L)))
            val isTimeValid = minutesBetween < VALID_CACHE_MINUTES
            if (isTimeValid.not()) {
                DatabaseHolder.get().pointsAreaDao()?.delete(cachedItem)
            }
            if (cachedItem == null || cachedItem.pointEntities.size == 0
                || MathUtils.calculateRadius(LocationModel(cachedItem.centerLat, cachedItem.centerLng),
                    LocationModel(cachedItem.topRightLat, cachedItem.topRightLng)) > 20_000) {
                loadPointsFromNetwork(centerPoint, screenRect)
            } else {
                val points = mutableListOf<RefillPointModel>()
                val iterator = cachedItem.pointEntities.iterator()
                while (iterator.hasNext()) {
                    val curItem = iterator.next()
                    if (curItem.location.toModel().inRect(screenRect)) {
                        points.add(curItem.toModel())
                    }
                }
                Single.just(points.toList())
            }
        }
    }

    private fun loadPointsFromNetwork(centerPoint: LocationModel, screenRect: ScreenRect): Single<List<RefillPointModel>> {
        return refillService.loadRefillPoints(centerPoint.latitude, centerPoint.longitude, MathUtils.calculateRadius(centerPoint, screenRect.topRight).toInt())
            .compose(httpClient.provideResponseValidator().networkTransformer())
            .map { points ->
                points.filter { it.location.toModel().inRect(screenRect) }
            }
            .map { points ->
                val seenPoints = DatabaseHolder.get().seenPointsDao()?.queryForAll() ?: emptyList()
                val partnerDao = DatabaseHolder.get().partnerDao()
                val pointModels = mutableListOf<RefillPointModel>()
                points.map { point ->
                    val partner = partnerDao?.queryForEq(PartnerEntity.ID_FIELD_NAME, point.partnerName)?.first()
                    if (partner == null) {
                        Timber.e("partner by name ${point.partnerName} for point = ${point.externalId} not found!")
                    } else {
                        val isSeen = seenPoints.find { it.pointId == point.externalId }?.isSeen ?: false
                        pointModels.add(point.toModel(partner.toModel(), isSeen))
                    }
                }
                pointModels.toList()
            }.doOnSuccess { models ->
                val area =  PointsInAreaEntities(centerPoint.latitude, centerPoint.longitude,
                    screenRect.topLeft.latitude, screenRect.topLeft.longitude,
                    screenRect.topRight.latitude, screenRect.topRight.longitude,
                    screenRect.bottomRight.latitude, screenRect.bottomRight.longitude,
                    screenRect.bottomLeft.latitude, screenRect.bottomLeft.longitude
                )
                DatabaseHolder.get().pointsAreaDao()?.createOrUpdate(area)
                val pointsDao = DatabaseHolder.get().refillPointsDao()
                val locationDao = DatabaseHolder.get().locationDao()
                val partnerDao = DatabaseHolder.get().partnerDao()
                models.forEach { point ->
                    val locationEntity = point.location.toEntity()
                    locationDao?.createOrUpdate(locationEntity)
                    val partner = partnerDao?.queryForEq(PartnerEntity.ID_FIELD_NAME, point.partner.id)?.first()
                    if (partner == null) {
                        Timber.e("partner by name ${point.partner.id} for point = ${point.externalId} not found!")
                    } else {
                        pointsDao?.createOrUpdate(point.toEntity(area, partner, locationEntity))
                    }

                }
            }
    }

    private fun LocationModel.inRect(screenRect: ScreenRect): Boolean {
        return this.latitude < screenRect.topLeft.latitude && this.longitude > screenRect.topLeft.longitude
                && this.latitude < screenRect.topRight.latitude && this.longitude < screenRect.topRight.longitude
                && this.latitude > screenRect.bottomRight.latitude && this.longitude < screenRect.bottomRight.longitude
                && this.latitude > screenRect.bottomLeft.latitude && this.longitude > screenRect.bottomLeft.longitude
    }

    private fun containsRect(cache: List<PointsInAreaEntities>, screenRect: ScreenRect): PointsInAreaEntities? {
        return cache.find { it.containsRect(screenRect) }
    }

    /*
    * проверка по времени самая простейшая и не учитывает перевод часов и прочие временные проблемы, так бы подключил скорее всего ThreeTen Android Backport, но в ТЗ указано ограниченное число либ, которые можно использовать
    * */
    private fun PointsInAreaEntities.containsRect(screenRect: ScreenRect): Boolean {
        return screenRect.topLeft.latitude <= this.topLeftLat
                && screenRect.topLeft.longitude >= this.topLeftLng
                && screenRect.topRight.latitude <= this.topRightLat
                && screenRect.topRight.longitude <= this.topRightLng
                && screenRect.bottomRight.latitude >= this.bottomRightLat
                && screenRect.bottomRight.longitude <= this.bottomRightLng
                && screenRect.bottomLeft.latitude >= this.bottomLeftLat
                && screenRect.bottomLeft.longitude >= this.bottomLeftLng
    }


    private fun loadPartners(): Single<List<PartnerEntity>> {
        return Single.fromCallable {
            DatabaseHolder.get()?.partnerDao()?.queryForAll() ?: emptyList()
        }.flatMap {
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
                val currencyEntity = it.currency.toEntity()
                helper.currencyDao()?.createOrUpdate(currencyEntity)
                helper.limitDao()?.createOrUpdate(it.toEntity(partnerEntity, currencyEntity))
            }
            partnerResponse.dailyLimits.forEach {
                val currencyEntity = it.currency.toEntity()
                helper.currencyDao()?.createOrUpdate(currencyEntity)
                helper.dailyLimitDao()?.createOrUpdate(it.toEntity(partnerEntity, currencyEntity))
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

    override fun updateRefillPoint(refillPointModel: RefillPointModel): Single<RefillPointModel> {
        return Single.fromCallable {
            DatabaseHolder.get().seenPointsDao()
                ?.createOrUpdate(RefillPointSeenEntity(refillPointModel.externalId, refillPointModel.isSeen))
        }.map {
            refillPointModel
        }
    }
}