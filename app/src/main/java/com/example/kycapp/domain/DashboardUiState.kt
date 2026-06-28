package com.example.kycapp.domain

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    object Empty : DashboardUiState()
    data class Success(val users: List<User>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}