package com.example.ece_452_project.data

import java.time.LocalDateTime

object DummyData {
    val users = listOf<User>(
        User(
            username = "alpha",
            password = "pass",
            name = "Alice",
            email = "a@mail.com",
            dietary = listOf("Halal"),
            schedule = listOf(
                Event(
                start = LocalDateTime.of(2023, 6, 26, 8, 30),
                end = LocalDateTime.of(2023, 6, 26, 16, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 6, 27, 8, 30),
                    end = LocalDateTime.of(2023, 6, 27, 16, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 6, 28, 8, 30),
                    end = LocalDateTime.of(2023, 6, 28, 16, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 6, 30, 8, 30),
                    end = LocalDateTime.of(2023, 6, 30, 16, 30)
                )
            )
        ),
        User(
            username = "bravo",
            password = "pass",
            name = "Bob",
            email = "b@mail.com",
            dietary = listOf<String>(),
            schedule = listOf(
                Event(
                    start = LocalDateTime.of(2023, 6, 29, 7, 30),
                    end = LocalDateTime.of(2023, 6, 29, 10, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 6, 29, 20, 30),
                    end = LocalDateTime.of(2023, 6, 29, 22, 0)
                ),
            )
        ),
        User(
            username = "charlie",
            password = "pass",
            name = "Charlie",
            email = "c@mail.com",
            dietary = listOf<String>("Vegetarian"),
            schedule = listOf(
                Event(
                    start = LocalDateTime.of(2023, 6, 27, 15, 0),
                    end = LocalDateTime.of(2023, 6, 27, 17, 30)
                ),
                Event(
                    start = LocalDateTime.of(2023, 6, 29, 15, 30),
                    end = LocalDateTime.of(2023, 6, 29, 16, 0)
                ),
            )
        )
    )
}