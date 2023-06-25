package com.example.ece_452_project.data

data class User (
    val username: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val dietary: List<String> = listOf<String>(),
    var schedule: MutableList<Event> = mutableListOf<Event>()
)