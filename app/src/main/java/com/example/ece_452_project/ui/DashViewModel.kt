package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.DashUiState
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    fun resetSelectedEvent() {
        eventName = ""
        eventDesc = ""
        deadlineDate = ""
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

    fun updateEventAttend(userName: String) {
        var tmp = _uiState.value.selectedEvent.copy()
        var myList = tmp.attend
        myList += userName
        tmp.attend = myList
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = tmp)
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
    fun updateDeadlineField(value: String) {
        deadlineDate = value
    }

    fun updateDeadlineDate(value: LocalDateTime) {
        //val date = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        var tmp = _uiState.value.selectedEvent.copy()
        tmp.deadline = value
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = tmp)
        }
    }

    fun updateEventSetting() {
        var tmp = _uiState.value.selectedEvent.copy()
        tmp.name = eventName
        tmp.description = eventDesc
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = tmp)
        }
    }

    fun updateSelectedEvent(value: Event) {
        _uiState.update { currentState ->
            currentState.copy(selectedEvent = value)
        }
    }

    fun updateUserSchedule(ev: Event) {
        var tmpSch = _uiState.value.user.schedule
        tmpSch.forEach{ event ->
            if (event.id == ev.id) {
                tmpSch.remove(event)
            }
        }
        tmpSch.add(ev)

        var tmpUser = _uiState.value.user
        tmpUser.schedule = tmpSch

        _uiState.update { currentState ->
            currentState.copy(user = tmpUser)
        }
    }
}