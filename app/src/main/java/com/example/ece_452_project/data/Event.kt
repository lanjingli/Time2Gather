package com.example.ece_452_project.data

import java.time.LocalDateTime

data class Event (
    var name: String = "",
    var description: String = "",
    var deadlineDate: String = "",
    var start: LocalDateTime = LocalDateTime.MIN,
    var end: LocalDateTime = LocalDateTime.MIN,
    var location: String = "",
    val shared: Boolean = false
)