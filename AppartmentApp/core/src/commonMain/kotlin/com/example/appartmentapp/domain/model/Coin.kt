package com.example.appartmentapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("market_cap")
    val marketCap: Long,
    @SerialName("market_cap_rank")
    val marketCapRank: Int,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double
)
