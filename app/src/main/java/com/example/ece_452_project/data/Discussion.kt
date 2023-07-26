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

    constructor(event: Event) : this(){
        name = event.name
        description = event.description
        deadline = event.deadline
        users = event.users
        options = mutableListOf(TimePlace(event))
        rankings = MutableList<List<Int>>(users.size) {List<Int>(options.size) {UNRANKED} }
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

    // assumes -1 is veto, 0 unranked, 1 is first choice, 2,3,4,etc. are worse choices
    // also requires that if rankings exist (e.g. not all -1 or 0), ranking descends
    // from 1 without gaps or duplicates. e.g. (3,0) or (1,4) invalid; (2,1,0,3) valid.
    fun finalize() : Event{
        // instant runoff voting
        var final = -1
        var votes = MutableList<Int>(options.size) {0}
        var eliminated = MutableList<Boolean>(options.size) {false}
        while (final == -1){
            //if only one not eliminated, choose as winner
            if (eliminated.count{!it} == 1){
                final = eliminated.indexOf(false)
                break
            }
            // retotal votes
            votes = MutableList<Int>(options.size) {0}
            rankings.forEach{ranking->
                var rank_count = 1
                var op_i = -1
                ranking.forEachIndexed{i, it->
                    if (it == rank_count) op_i = i
                }
                while (op_i != -1){
                    if (!eliminated[op_i]){
                        votes[op_i] += 1
                        break
                    }
                    rank_count += 1
                    op_i = -1
                    ranking.forEach{
                        if (it == rank_count) op_i = it
                    }
                }
            }
            // if candidate reaches majority, wins
            votes.forEachIndexed{i,count->
                if (count > users.size/2) final = i
            }

            // eliminate one
            var worst = votes.max()
            var worst_i = 0
            votes.forEachIndexed{i,count->
                if (!eliminated[i] && count < worst){
                    worst = count
                    worst_i = i
                }
            }
            eliminated[worst_i] = true
        }
        // clear any vetoing users from final event
        if (final == -1) final = 0
        var attending = MutableList<Boolean>(users.size) {true}
        rankings.forEachIndexed { i, ranking ->
            if (ranking[i] == VETO_RANK){
                attending[i] = false
            }
        }
        return Event(
            name = name,
            description = description,
            start = options[final].start,
            end = options[final].end,
            location = options[final].location,
            shared = true,
            users = users.filterIndexed{i, _-> attending[i]}
        )
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