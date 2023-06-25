package com.example.ece_452_project.ui

import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.DashUiState
import com.example.ece_452_project.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashUiState())
    val uiState: StateFlow<DashUiState> = _uiState.asStateFlow()

    fun resetDash(){
        _uiState.value = DashUiState()
    }

    fun updateUser(user: User){
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }
}