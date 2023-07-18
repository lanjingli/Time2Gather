package com.example.ece_452_project

import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import java.time.YearMonth

fun generateAllEventsMap( User: User  ): List<Event> = buildList{

    for (events in User.schedule){
        add(events)
    }

}

fun generateSharedEventsMap( User: User  ): List<Event> = buildList{

    for (events in User.schedule){

        if (events.shared) {
            add(events)
        }
    }
}

fun generateIndEventsMap( User: User  ): List<Event> = buildList{

    for (events in User.schedule){

        if (!events.shared) {
            add(events)
        }
    }
}