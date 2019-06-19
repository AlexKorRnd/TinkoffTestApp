package com.example.refillpoints.domain.models

data class PartnerModel(
    val id: String,
    val name: String,
    val picture: String,
    val url: String
) {

    // // TODO: 19.06.19: temporary solution(need create picture url provider)
    companion object {
        const val PICTURE_URL_PREFIX = "https://static.tinkoff.ru/icons/deposition-partners-v3"
    }

}