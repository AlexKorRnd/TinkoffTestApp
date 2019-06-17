package com.example.refillpoints.data.network.responses

data class PartnerResponse(
    val dailyLimits: List<DailyLimitResponse>,
    val depositionDuration: String,
    val description: String,
    val externalPartnerId: String,
    val hasLocations: Boolean,
    val hasPreferentialDeposition: Boolean,
    val id: String,
    val isMomentary: Boolean,
    val limitations: String,
    val limits: List<LimitResponse>,
    val moneyMax: Int,
    val moneyMin: Int,
    val name: String,
    val picture: String,
    val pointType: String,
    val url: String
)

data class LimitResponse(
    val currency: CurrencyResponse,
    val max: Int,
    val min: Int
)

data class CurrencyResponse(
    val code: Int,
    val name: String,
    val strCode: String
)

data class DailyLimitResponse(
    val amount: Int,
    val currency: CurrencyResponse
)

