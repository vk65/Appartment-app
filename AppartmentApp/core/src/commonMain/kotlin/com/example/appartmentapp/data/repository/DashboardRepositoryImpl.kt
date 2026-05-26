package com.example.appartmentapp.data.repository

import com.example.appartmentapp.domain.model.DashboardData
import com.example.appartmentapp.domain.repository.DashboardRepository
import kotlinx.coroutines.delay

class DashboardRepositoryImpl : DashboardRepository {
    override suspend fun getDashboardData(): Result<DashboardData> {
        delay(500) // Simulate network delay
        return Result.success(
            DashboardData(
                welcomeMessage = "Welcome to your Apartment App!",
                upcomingEvents = listOf("Rent due in 3 days", "Pool party on Saturday", "Maintenance check on Monday"),
                notificationsCount = 5
            )
        )
    }
}
