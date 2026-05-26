package com.example.appartmentapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appartmentapp.domain.model.DashboardData
import com.example.appartmentapp.domain.usecase.GetDashboardDataUseCase
import com.example.appartmentapp.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            val result = getDashboardDataUseCase()
            _uiState.value = if (result.isSuccess) {
                DashboardUiState.Success(result.getOrThrow())
            } else {
                DashboardUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase()
            onLogoutSuccess()
        }
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val data: DashboardData) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
