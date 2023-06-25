package com.example.ece_452_project.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class Event (
    val name: String = "",
    var start: LocalDateTime = LocalDateTime.MIN,
    var end: LocalDateTime = LocalDateTime.MIN,
    var location: String = "",
    val shared: Boolean = false
)