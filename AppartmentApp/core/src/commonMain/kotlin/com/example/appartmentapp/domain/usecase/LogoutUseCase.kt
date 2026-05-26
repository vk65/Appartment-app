package com.example.appartmentapp.domain.usecase

import com.example.appartmentapp.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
