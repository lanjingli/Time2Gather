package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.EventUiState
import com.example.ece_452_project.data.MenuData
import com.example.ece_452_project.data.User
import com.example.ece_452_project.data.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()
    var invited: MutableList<Boolean> = mutableListOf()

    var eventName by mutableStateOf("")
        private set

    var eventDesc by mutableStateOf("")
        private set
    var decisionDeadline by mutableStateOf("")
        private set
    var timeToAlloc by mutableStateOf("")
        private set
    var startDate by mutableStateOf("")
        private set
    var endDate by mutableStateOf("")
        private set
    //var InvitedFriends by mutableStateOf(mutableMapOf<String, Boolean>())
//    var invited by mutableStateOf(mutableListOf<Boolean>())
//        private set
//    MutableList<Person> = mutableListOf()
//    var invited by mutableStateOf(List<Boolean>(MenuData.dietaryOptions.size) {false})
//        private set


    fun resetEvent(){
        _uiState.value = EventUiState()
    }


    fun updateEventName(value: String) { eventName = value }

    fun updateEventDesc(value: String) { eventDesc = value }
    fun updateDecisionDeadline(value: String) { decisionDeadline = value }
    fun updateTimeToAlloc(value: String) { timeToAlloc = value }
    fun updateStartDate(value: String) {startDate = value}
    fun updateEndDate(value: String) {endDate = value}

    fun updateInvitedList(user: User, value: Boolean, i: Int) {
        if (i >= 0 && i < user.friends.size) {
            var temp = invited
            temp[i] = value
            invited = temp
        }
    }

    fun updateEventAfterInvite(user: User){
        val tmpFriends = mutableListOf<String>()
        invited.forEachIndexed(){i,it->
            if (it) tmpFriends.add(user.friends[i])
        }

        val tmpEvent = Event(
            name = eventName,
            description=eventDesc,
            decisionDeadline=decisionDeadline,
            timeToAlloc=timeToAlloc,
            startDate =startDate,
            endDate = endDate,
            invitedFriends= tmpFriends,
        )

        _uiState.update { currentState ->
            currentState.copy(event = tmpEvent)
        }
    }

    fun createEventFromInputs(user:User){
        val tmpFriends = mutableListOf<String>()
        invited.forEachIndexed(){i,it->
            if (it) tmpFriends.add(user.friends[i])
        }

        val tmpEvent = Event(
            name = eventName,
            description=eventDesc,
            decisionDeadline=decisionDeadline,
            timeToAlloc=timeToAlloc,
            startDate =startDate,
            endDate = endDate,
            invitedFriends= tmpFriends,
        )

        // Only add event if event name doesn't already exist
        var flag = false
        user.schedule.forEach{event ->
            if (eventName == event.name) {flag = true}
        }
        if(!flag){user.schedule.add(tmpEvent)}


        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun updateUser(user: User){
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun updateEvent(event: Event){
        _uiState.update { currentState ->
            currentState.copy(event = event)
        }
    }
}