package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.DashUiState
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashUiState())
    val uiState: StateFlow<DashUiState> = _uiState.asStateFlow()

    var curEventName by mutableStateOf("")
        private set

    fun resetDash(){
        _uiState.value = DashUiState()
    }

    fun updateUser(user: User){
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun updateEvent(event: Event) {
        _uiState.update {currentState ->
            currentState.copy(event = event)

        }
    }

    fun fetchCurEvent(user: User){
        user.schedule.forEach{it->
            if(it.name == curEventName) {
                updateEvent(it)
            }
        }
    }
}