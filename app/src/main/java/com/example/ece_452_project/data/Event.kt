package com.example.ece_452_project.data

import com.example.ece_452_project.remote.RemoteEvent
import java.time.LocalDateTime
import java.time.ZoneId

data class Event (
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var deadline: String = "",
    var eventOwner: String = "",
    var start: LocalDateTime = LocalDateTime.MIN,
    var end: LocalDateTime = LocalDateTime.MIN,
    var location: String = "",
    var shared: Boolean = false,
    var users: List<String> = listOf<String>(),
    var attend: List<String> = listOf<String>(),
) {
    constructor (event: RemoteEvent) : this(){
        event.id?.let{id = it}
        event.name?.let{name = it}
        event.description?.let{description = it}
        event.deadline?.let{
            deadline = it
        }
        event.eventOwner?.let{eventOwner = it}
        event.location?.let{location = it}
        event.isShared?.let{shared = it}
        event.users?.let{users = it}
        event.attend?.let{attend = it}
        event.start?.let{
            start = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
        event.end?.let{
            end = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
    }
}