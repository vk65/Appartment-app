package com.example.appartmentapp.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appartmentapp.domain.usecase.SignupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val signupUseCase: SignupUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading
            val result = signupUseCase(name, email, password)
            _uiState.value = if (result.isSuccess) {
                SignupUiState.Success
            } else {
                SignupUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _uiState.value = SignupUiState.Idle
    }
}

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    object Success : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}
