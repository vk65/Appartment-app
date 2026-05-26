package com.example.appartmentapp.domain.repository

import com.example.appartmentapp.domain.model.DashboardData

interface DashboardRepository {
    suspend fun getDashboardData(): Result<DashboardData>
}
