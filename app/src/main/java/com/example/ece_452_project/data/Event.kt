package com.example.ece_452_project.data

import com.example.ece_452_project.remote.RemoteEvent
import java.time.LocalDateTime
import java.time.ZoneId

data class Event (
    var name: String = "",
    var description: String = "",
    var start: LocalDateTime = LocalDateTime.MIN,
    var end: LocalDateTime = LocalDateTime.MIN,
    var location: String = "",
    var shared: Boolean = false,
    var users: List<String> = listOf<String>()
) {
    constructor (event: RemoteEvent) : this(){
        event.name?.let{name = it}
        event.description?.let{description = it}
        event.location?.let{location = it}
        event.isShared?.let{shared = it}
        event.users?.let{users = it}
        event.start?.let{
            start = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
        event.end?.let{
            end = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
    }
}