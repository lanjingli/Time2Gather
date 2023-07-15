package com.example.ece_452_project

import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import java.time.YearMonth

fun generateEventsList( User: User  ): List<Event> = buildList{

    for (events in User.schedule){
        add(events)
    }

}