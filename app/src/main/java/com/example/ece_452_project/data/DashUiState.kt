package com.example.ece_452_project.data

data class DashUiState (
    val user: User = User(),
    val selectedFriends: List<User> = listOf<User>(),
    val selectedEvent: Event = Event(shared = true)
)