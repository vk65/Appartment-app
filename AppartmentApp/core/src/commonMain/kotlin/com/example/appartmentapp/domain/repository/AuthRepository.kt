package com.example.appartmentapp.domain.repository

import com.example.appartmentapp.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signup(name: String, email: String, password: String): Result<User>
    suspend fun forgotPassword(email: String): Result<Unit>
    suspend fun verifyOtp(email: String, otp: String): Result<Unit>
    suspend fun resetPassword(email: String, newPassword: String): Result<Unit>
    suspend fun getCurrentUser(): User?
    suspend fun logout()
}
