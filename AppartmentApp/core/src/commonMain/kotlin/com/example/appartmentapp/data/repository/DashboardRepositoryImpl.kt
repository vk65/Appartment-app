package com.example.appartmentapp.data.repository

import com.example.appartmentapp.domain.model.Coin
import com.example.appartmentapp.domain.model.DashboardData
import com.example.appartmentapp.domain.repository.DashboardRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class DashboardRepositoryImpl : DashboardRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    override suspend fun getDashboardData(): Result<DashboardData> {
        return try {
            val coins: List<Coin> = client.get("https://api.coingecko.com/api/v3/coins/markets") {
                parameter("vs_currency", "usd")
                parameter("order", "market_cap_desc")
                parameter("per_page", 10)
                parameter("page", 1)
                parameter("sparkline", false)
            }.body()

            Result.success(
                DashboardData(
                    welcomeMessage = "Welcome to your Apartment App!",
                    upcomingEvents = listOf("Rent due in 3 days", "Pool party on Saturday", "Maintenance check on Monday"),
                    notificationsCount = 5,
                    topCoins = coins
                )
            )
        } catch (e: Exception) {
            // Fallback to local data if network fails or just return error
            Result.success(
                DashboardData(
                    welcomeMessage = "Welcome to your Apartment App! (Offline)",
                    upcomingEvents = listOf("Rent due in 3 days", "Pool party on Saturday", "Maintenance check on Monday"),
                    notificationsCount = 5,
                    topCoins = emptyList()
                )
            )
        }
    }
}
