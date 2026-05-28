package com.example.appartmentapp.domain.model

data class DashboardData(
    val welcomeMessage: String,
    val upcomingEvents: List<String>,
    val notificationsCount: Int,
    val topCoins: List<Coin> = emptyList()
)
