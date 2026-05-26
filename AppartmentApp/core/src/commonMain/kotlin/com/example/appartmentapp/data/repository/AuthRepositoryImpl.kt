package com.example.appartmentapp.data.repository

import com.example.appartmentapp.domain.model.User
import com.example.appartmentapp.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    private var currentUser: User? = null

    override suspend fun login(email: String, password: String): Result<User> {
        delay(1000) // Simulate network delay
        return if (email == "test@example.com" && password == "password") {
            val user = User("1", email, "Demo User")
            currentUser = user
            Result.success(user)
        } else {
            Result.failure(Exception("Invalid credentials. Use test@example.com / password"))
        }
    }

    override suspend fun signup(name: String, email: String, password: String): Result<User> {
        delay(1000)
        val user = User("2", email, name)
        currentUser = user
        return Result.success(user)
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        delay(1000)
        return if (email.contains("@")) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid email address"))
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): Result<Unit> {
        delay(1000)
        return if (otp == "1234") {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid OTP. Use 1234"))
        }
    }

    override suspend fun resetPassword(email: String, newPassword: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    override suspend fun getCurrentUser(): User? = currentUser

    override suspend fun logout() {
        currentUser = null
    }
}
