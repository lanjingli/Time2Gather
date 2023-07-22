package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.DashUiState
import com.example.ece_452_project.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class DashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashUiState())
    val uiState: StateFlow<DashUiState> = _uiState.asStateFlow()

    var eventName by mutableStateOf("")
        private set
    var eventDesc by mutableStateOf("")
        private set
    var deadlineDate by mutableStateOf("")
        private set

    fun resetDash() {
        _uiState.value = DashUiState()
    }

    fun updateUser(user: User) {
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun updateSelectedFriends(friends: List<User>) {
        _uiState.update { currentState ->
            currentState.copy(selectedFriends = friends)
        }
    }

    fun updateSelectedTime(start: LocalDateTime, end: LocalDateTime) {
        var tmp = _uiState.value.selectedEvent.copy()
        tmp.start = start
        tmp.end = end
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = tmp)
        }
    }

    fun updateSelectedPlace(place: String) {
        var tmp = _uiState.value.selectedEvent.copy()
        tmp.location = place
        tmp.name = place
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = tmp)
        }
    }

    fun updateEventName(value: String) {
        eventName = value
    }

    fun updateEventDescription(value: String) {
        eventDesc = value
    }

    fun updateDeadlineDate(value: String) {
        deadlineDate = value
    }

    fun updateEventSetting() {
        var tmp = _uiState.value.selectedEvent.copy()
        tmp.name = eventName
        tmp.description = eventDesc
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = tmp)
        }
    }
}