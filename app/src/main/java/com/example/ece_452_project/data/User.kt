package com.example.ece_452_project.data

data class User (
    val username: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val dietary: List<String> = listOf<String>(),
    val schedule: List<Event> = listOf<Event>()
)