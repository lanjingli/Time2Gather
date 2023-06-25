package com.example.ece_452_project.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class Event (
    val name: String = "",
    val description: String = "",
    val decisionDeadline: String = "",
    val timeToAlloc: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val start: LocalDateTime = LocalDateTime.MIN,
    val end: LocalDateTime = LocalDateTime.MIN,
    val location: String = "",
    val invitedFriends: MutableList<String> = mutableListOf("")
)