package com.example.ece_452_project.data

import java.time.LocalDateTime

data class DummyPlace (
    val name: String = "",
    val options: List<String> = listOf<String>(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
object DummyData {
    var users = mutableListOf<User>(
        User(
            username = "alpha",
            password = "password",
            name = "Alice",
            email = "a@mail.com",
            dietary = listOf("Halal"),
            friends = listOf("bravo", "charlie", "delta"),
            schedule = mutableListOf(
                Event(
                    name = "Lectures",
                    start = LocalDateTime.of(2023, 7, 17, 8, 30),
                    end = LocalDateTime.of(2023, 7, 17, 16, 30)
                ),
                Event(
                    name = "Labs",
                    start = LocalDateTime.of(2023, 7, 27, 9, 30),
                    end = LocalDateTime.of(2023, 7, 27, 11, 30)
                ),
                Event(
                    name = "Tutorials",
                    start = LocalDateTime.of(2023, 7, 27, 13, 30),
                    end = LocalDateTime.of(2023, 7, 27, 16, 30)
                ),
                Event(
                    name = "Social",
                    start = LocalDateTime.of(2023, 7, 28, 10, 30),
                    end = LocalDateTime.of(2023, 7, 28, 18, 30)
                ),
                Event(
                    name = "Job Fair",
                    start = LocalDateTime.of(2023, 7, 30, 11, 30),
                    end = LocalDateTime.of(2023, 7, 30, 14, 30)
                )
            )
        ),
        User(
            username = "bravo",
            password = "password",
            name = "Bob",
            email = "b@mail.com",
            dietary = listOf<String>(),
            friends = listOf("alpha", "charlie"),
            schedule = mutableListOf(
                Event(
                    start = LocalDateTime.of(2023, 7, 17, 7, 30),
                    end = LocalDateTime.of(2023, 7, 17, 10, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 7, 29, 20, 30),
                    end = LocalDateTime.of(2023, 7, 29, 22, 0)
                ),
            )
        ),
        User(
            username = "charlie",
            password = "password",
            name = "Charlie",
            email = "c@mail.com",
            dietary = listOf<String>("Vegetarian"),
            friends = listOf("alpha", "bravo"),
            schedule = mutableListOf(
                Event(
                    start = LocalDateTime.of(2023, 7, 17, 15, 0),
                    end = LocalDateTime.of(2023, 7, 17, 17, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 7, 29, 15, 30),
                    end = LocalDateTime.of(2023, 7, 29, 16, 0)
                ),
            )
        ),
        User(
            username = "delta",
            password = "password",
            name = "Dahlia",
            email = "d@mail.com",
            dietary = listOf<String>(),
            friends = listOf("alpha"),
            schedule = mutableListOf(
                Event(
                    name = "FYDP Brainstorming",
                    start = LocalDateTime.of(2023, 7, 1, 15, 0),
                    end = LocalDateTime.of(2023, 7, 1, 17, 30),
                    shared = true,
                    users = listOf("alpha")
                ),
                Event(
                    name = "Ice Cream Meet",
                    start = LocalDateTime.of(2023, 7, 5, 15, 30),
                    end = LocalDateTime.of(2023, 7, 5, 16, 0),
                    shared = true,
                    users = listOf("bravo")
                ),
                Event(
                    name = "Plaza Lunch",
                    start = LocalDateTime.of(2023, 7, 17, 12, 30),
                    end = LocalDateTime.of(2023, 7, 17, 13, 30),
                    shared = true,
                    users = listOf("charlie")
                ),
                Event(
                    name = "Group Study",
                    start = LocalDateTime.of(2023, 7, 20, 15, 30),
                    end = LocalDateTime.of(2023, 7, 20, 16, 0),
                    shared = true,
                    users = listOf("alpha")
                ),
            )
        )
    )
    val places = listOf<DummyPlace>(
        DummyPlace(
            name = "Campus Pizza",
            options = listOf("Vegetarian", "Halal"),
            latitude = 43.4721753,
            longitude = -80.5405827
    ),
        DummyPlace(
            name = "Kismet",
            options = listOf("Gluten-Free", "Vegetarian", "Halal"),
            latitude = 43.472623,
            longitude = -80.5397511
        ),
        DummyPlace(
            name = "Subway",
            options = listOf("Vegetarian"),
            latitude = 43.4722921,
            longitude = -80.5404086
        )
    )

    val times = listOf<LocalDateTime>(
        LocalDateTime.of(2023, 7, 28, 18, 0),
        LocalDateTime.of(2023, 7, 30, 19, 0),
        LocalDateTime.of(2023, 7, 29, 15, 0)
    )
}