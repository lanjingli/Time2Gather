package com.example.ece_452_project.ui

import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.MapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun resetDash(){
        _uiState.value = MapUiState()
    }
}