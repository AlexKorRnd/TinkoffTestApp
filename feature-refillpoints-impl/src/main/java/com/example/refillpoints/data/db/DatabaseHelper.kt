package com.example.refillpoints.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.refillpoints.data.db.models.*
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import timber.log.Timber
import java.sql.SQLException

class DatabaseHelper(
    context: Context
) : OrmLiteSqliteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "tinkofftestapp.db"
        private const val DB_VERSION = 2
    }

    private var refillPointsDao: Dao<RefillPointEntity, Int>? = null
    private var partnerDao: Dao<PartnerEntity, Int>? = null
    private var locationDao: Dao<LocationEntity, Int>? = null
    private var limitDao: Dao<LimitEntity, Int>? = null
    private var dailyLimitDao: Dao<DailyLimitEntity, Int>? = null
    private var currencyDao: Dao<CurrencyEntity, Int>? = null
    private var seenPointsDao: Dao<RefillPointSeenEntity, Int>? = null
    private var pointsAreaDao: Dao<PointsInAreaEntities, Int>? = null

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            TableUtils.createTable(connectionSource, RefillPointEntity::class.java)
            TableUtils.createTable(connectionSource, PartnerEntity::class.java)
            TableUtils.createTable(connectionSource, LocationEntity::class.java)
            TableUtils.createTable(connectionSource, LimitEntity::class.java)
            TableUtils.createTable(connectionSource, DailyLimitEntity::class.java)
            TableUtils.createTable(connectionSource, CurrencyEntity::class.java)
            TableUtils.createTable(connectionSource, RefillPointSeenEntity::class.java)
            TableUtils.createTable(connectionSource, PointsInAreaEntities::class.java)
        } catch (e: SQLException) {
            Timber.e(e)
        }
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        // TODO: 20.06.19 realize it
    }

    @Synchronized
    fun refillPointsDao(): Dao<RefillPointEntity, Int>? {
        if (refillPointsDao == null) {
            refillPointsDao = getDao(RefillPointEntity::class.java)
        }
        return refillPointsDao
    }

    @Synchronized
    fun partnerDao(): Dao<PartnerEntity, Int>? {
        if (partnerDao == null) {
            partnerDao = getDao(PartnerEntity::class.java)
        }
        return partnerDao
    }

    @Synchronized
    fun locationDao(): Dao<LocationEntity, Int>? {
        if (locationDao == null) {
            locationDao = getDao(LocationEntity::class.java)
        }
        return locationDao
    }

    @Synchronized
    fun limitDao(): Dao<LimitEntity, Int>? {
        if (limitDao == null) {
            limitDao = getDao(LimitEntity::class.java)
        }
        return limitDao
    }

    @Synchronized
    fun dailyLimitDao(): Dao<DailyLimitEntity, Int>? {
        if (dailyLimitDao == null) {
            dailyLimitDao = getDao(DailyLimitEntity::class.java)
        }
        return dailyLimitDao
    }

    @Synchronized
    fun currencyDao(): Dao<CurrencyEntity, Int>? {
        if (currencyDao == null) {
            currencyDao = getDao(CurrencyEntity::class.java)
        }
        return currencyDao
    }

    @Synchronized
    fun seenPointsDao(): Dao<RefillPointSeenEntity, Int>? {
        if (seenPointsDao == null) {
            seenPointsDao = getDao(RefillPointSeenEntity::class.java)
        }
        return seenPointsDao
    }

    @Synchronized
    fun pointsAreaDao(): Dao<PointsInAreaEntities, Int>? {
        if (pointsAreaDao == null) {
            pointsAreaDao = getDao(PointsInAreaEntities::class.java)
        }
        return pointsAreaDao
    }
}