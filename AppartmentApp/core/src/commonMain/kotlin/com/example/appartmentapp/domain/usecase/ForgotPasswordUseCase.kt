package com.example.appartmentapp.domain.usecase

import com.example.appartmentapp.domain.repository.AuthRepository

class ForgotPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(Exception("Email cannot be empty"))
        }
        return repository.forgotPassword(email)
    }
}
