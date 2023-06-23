package com.example.ece_452_project.ui

import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.DashUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashUiState())
    val uiState: StateFlow<DashUiState> = _uiState.asStateFlow()

    fun resetDash(){
        _uiState.value = DashUiState()
    }
}