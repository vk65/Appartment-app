package com.example.appartmentapp.domain.usecase

import com.example.appartmentapp.domain.repository.AuthRepository

class ResetPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, newPassword: String): Result<Unit> {
        if (newPassword.length < 6) {
            return Result.failure(Exception("Password must be at least 6 characters"))
        }
        return repository.resetPassword(email, newPassword)
    }
}
