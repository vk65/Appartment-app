package com.example.appartmentapp.domain.usecase

import com.example.appartmentapp.domain.model.DashboardData
import com.example.appartmentapp.domain.repository.DashboardRepository

class GetDashboardDataUseCase(private val repository: DashboardRepository) {
    suspend operator fun invoke(): Result<DashboardData> {
        return repository.getDashboardData()
    }
}
