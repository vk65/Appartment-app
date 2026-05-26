package com.example.appartmentapp.domain.usecase

import com.example.appartmentapp.domain.repository.AuthRepository

class VerifyOtpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, otp: String): Result<Unit> {
        if (otp.isBlank()) {
            return Result.failure(Exception("OTP cannot be empty"))
        }
        return repository.verifyOtp(email, otp)
    }
}
