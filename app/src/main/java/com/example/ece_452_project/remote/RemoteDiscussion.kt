package com.example.ece_452_project.remote

import com.example.ece_452_project.data.Discussion
import com.example.ece_452_project.data.TimePlace
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import java.time.LocalDateTime
import java.time.ZonedDateTime

@IgnoreExtraProperties
data class RemoteDiscussion (
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var deadline: Timestamp? = null,
    var users: List<String>? = null,
    var options: List<RemoteTimePlace>? = null,
    var rankings: List<Int>? = null
){
    constructor(discussion: Discussion) : this(){
        id = discussion.id
        description = discussion.description
        name = discussion.name
        deadline = Timestamp(discussion.deadline.toEpochSecond(ZonedDateTime.now().offset), 0)
        users = discussion.users
        options = discussion.options.map {RemoteTimePlace(it)}
        var r = mutableListOf<Int>()
        discussion.rankings.forEach{it.forEach{i->r.add(i)}}
        rankings = r.toList()
    }

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_DESCRIPTION = "description"
        const val FIELD_DEADLINE = "deadline"
        const val FIELD_USERS = "users"
        const val FIELD_OPTIONS = "options"
        const val FIELD_RANKINGS = "rankings"
    }
}

@IgnoreExtraProperties
data class RemoteTimePlace (
    var start: Timestamp? = null,
    var end: Timestamp? = null,
    var location: String? = null
){
    constructor(timePlace: TimePlace) : this(){
        start = Timestamp(timePlace.start.toEpochSecond(ZonedDateTime.now().offset), 0)
        end = Timestamp(timePlace.end.toEpochSecond(ZonedDateTime.now().offset), 0)
        location = timePlace.location
    }

    companion object {
        const val FIELD_LOCATION = "location"
        const val FIELD_START = "start"
        const val FIELD_END = "end"
    }
}

data class RemoteTimePlaces(
    var tps : List<RemoteTimePlace>
)