package com.example.ece_452_project.remote

import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoField
import java.util.TimeZone

@IgnoreExtraProperties
data class RemoteEvent (
    var name : String? = null,
    var description : String? = null,
    var start : Timestamp? = null,
    var end : Timestamp? = null,
    var location : String? = null,
    @field:JvmField
    var isShared : Boolean? = null,
    var users: List<String>? = null
) {
    constructor(event: Event, user: User? = null) : this() {
        name = event.name
        description = event.description
        start = Timestamp(event.start.toEpochSecond(ZonedDateTime.now().offset), 0)
        end = Timestamp(event.end.toEpochSecond(ZonedDateTime.now().offset), 0)
        location = event.location
        isShared = event.shared
        users = event.users
        user?.let{
            users = event.users.plus(user.username)
        }
    }

    companion object {
        const val FIELD_NAME = "name"
        const val FIELD_DESCRIPTION = "description"
        const val FIELD_START = "start"
        const val FIELD_END = "end"
        const val FIELD_LOCATION = "location"
        const val FIELD_SHARED = "isShared"
        const val FIELD_USERS = "users"
    }
}