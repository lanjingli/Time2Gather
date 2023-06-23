package com.example.ece_452_project.ui

import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.ScheduleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    fun resetDash(){
        _uiState.value = ScheduleUiState()
    }
}