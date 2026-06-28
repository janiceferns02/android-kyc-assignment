package com.example.kycapp.ui.accountlistdashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kycapp.domain.DashboardUiState
import com.example.kycapp.domain.KycStatus
import com.example.kycapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(KycStatus.PENDING)
    val selectedTab = _selectedTab.asStateFlow()

    val uiState: StateFlow<DashboardUiState> = combine(
        userRepository.getUsers(), _selectedTab
    ) { users, tab ->
        if (users.isEmpty()) {
            DashboardUiState.Empty
        } else {
            val filteredList = users.filter { user -> user.kycStatus == tab }

            if (filteredList.isEmpty()) {
                DashboardUiState.Empty
            } else {
                DashboardUiState.Success(users = filteredList)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )

    init {
        refreshData()
    }

    fun onTabChanged(newTab: KycStatus) {
        _selectedTab.value = newTab
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                userRepository.refreshUsers()
            } catch (e: Exception) {

            }
        }
    }
}