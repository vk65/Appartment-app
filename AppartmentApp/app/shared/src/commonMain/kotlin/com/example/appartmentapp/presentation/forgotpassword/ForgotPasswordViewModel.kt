package com.example.appartmentapp.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appartmentapp.domain.usecase.ForgotPasswordUseCase
import com.example.appartmentapp.domain.usecase.ResetPasswordUseCase
import com.example.appartmentapp.domain.usecase.VerifyOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private var userEmail: String = ""

    fun sendOtp(email: String) {
        userEmail = email
        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            val result = forgotPasswordUseCase(email)
            _uiState.value = if (result.isSuccess) {
                ForgotPasswordUiState.OtpSent
            } else {
                ForgotPasswordUiState.Error(result.exceptionOrNull()?.message ?: "Failed to send OTP")
            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            val result = verifyOtpUseCase(userEmail, otp)
            _uiState.value = if (result.isSuccess) {
                ForgotPasswordUiState.OtpVerified
            } else {
                ForgotPasswordUiState.Error(result.exceptionOrNull()?.message ?: "Invalid OTP")
            }
        }
    }

    fun resetPassword(newPassword: String) {
        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            val result = resetPasswordUseCase(userEmail, newPassword)
            _uiState.value = if (result.isSuccess) {
                ForgotPasswordUiState.Success
            } else {
                ForgotPasswordUiState.Error(result.exceptionOrNull()?.message ?: "Failed to reset password")
            }
        }
    }

    fun resetToIdle() {
        _uiState.value = ForgotPasswordUiState.Idle
    }
}

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    object OtpSent : ForgotPasswordUiState()
    object OtpVerified : ForgotPasswordUiState()
    object Success : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}
