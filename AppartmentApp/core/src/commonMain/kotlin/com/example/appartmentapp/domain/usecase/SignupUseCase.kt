package com.example.appartmentapp.domain.usecase

import com.example.appartmentapp.domain.model.User
import com.example.appartmentapp.domain.repository.AuthRepository

class SignupUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<User> {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("All fields are required"))
        }
        return repository.signup(name, email, password)
    }
}
