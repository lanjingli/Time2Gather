package com.example.ece_452_project.data

import com.example.ece_452_project.remote.RemoteDiscussion
import com.example.ece_452_project.remote.RemoteTimePlace
import java.time.LocalDateTime
import java.time.ZoneId

data class Discussion (
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var deadline: LocalDateTime = LocalDateTime.MIN,
    var users: List<String> = listOf<String>(),
    var options: MutableList<TimePlace> = mutableListOf<TimePlace>(),
    var rankings: MutableList<List<Int>> = mutableListOf<List<Int>>()
){
    constructor(discussion: RemoteDiscussion) : this(){
        discussion.id?.let{id = it}
        discussion.name?.let{name = it}
        discussion.description?.let{description = it}
        discussion.deadline?.let{
            deadline = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
        discussion.users?.let{users = it}
        discussion.options?.let{options = it.map {tp->TimePlace(tp)}.toMutableList()}
        discussion.rankings?.let{
            for (i in 1..users.size){
                var l = listOf<Int>()
                for (j in 1..options.size){
                    l = l.plus(it[(i-1)*options.size + (j-1)])
                }
                rankings.add(l)
            }
        }
    }

    fun addOption(event: Event){
        val tp = TimePlace(event)
        options.add(tp)
        rankings.add(listOf())
    }

    fun setRanking(user: User, ranking : List<Int>){
        if (ranking.size == options.size){
            val i = users.indexOf(user.username)
            rankings[i] = ranking
        }
    }

    fun getRanking(user: User) : List<Int>{
        val i = users.indexOf(user.username)
        return rankings[i]
    }

    companion object {
        const val VETO_RANK = -1
        const val UNRANKED = 0
    }
}

data class TimePlace (
    var start: LocalDateTime = LocalDateTime.MIN,
    var end: LocalDateTime = LocalDateTime.MIN,
    var location: String = ""
){
    constructor(timePlace: RemoteTimePlace) : this(){
        timePlace.start?.let{
            start = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
        timePlace.end?.let{
            end = it.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
        timePlace.location?.let{location = it}
    }

    constructor(event: Event) : this(){
        start = event.start
        end = event.end
        location = event.location
    }
}