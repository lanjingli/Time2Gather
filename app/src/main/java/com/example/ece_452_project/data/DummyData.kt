package com.example.ece_452_project.data

import java.time.LocalDateTime

data class DummyPlace (
    val name: String = "",
    val options: List<String> = listOf<String>()
)
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
                    name = "Lectures",
                    start = LocalDateTime.of(2023, 6, 26, 8, 30),
                    end = LocalDateTime.of(2023, 6, 26, 16, 30)
                ),
                Event(
                    name = "Labs",
                    start = LocalDateTime.of(2023, 6, 27, 9, 30),
                    end = LocalDateTime.of(2023, 6, 27, 11, 30)
                ),
                Event(
                    name = "Tutorials",
                    start = LocalDateTime.of(2023, 6, 27, 13, 30),
                    end = LocalDateTime.of(2023, 6, 27, 16, 30)
                ),
                Event(
                    name = "Social",
                    start = LocalDateTime.of(2023, 6, 28, 10, 30),
                    end = LocalDateTime.of(2023, 6, 28, 18, 30)
                ),
                Event(
                    name = "Job Fair",
                    start = LocalDateTime.of(2023, 6, 30, 11, 30),
                    end = LocalDateTime.of(2023, 6, 30, 14, 30)
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
        ),
        User(
            username = "delta",
            password = "pass",
            name = "Dahlia",
            email = "d@mail.com",
            dietary = listOf<String>(""),
            schedule = listOf(
                Event(
                    start = LocalDateTime.of(2023, 7, 1, 15, 0),
                    end = LocalDateTime.of(2023, 7, 1, 17, 30),
                    shared = true
                ),
                Event(
                    start = LocalDateTime.of(2023, 7, 5, 15, 30),
                    end = LocalDateTime.of(2023, 7, 5, 16, 0),
                    shared = true
                ),
            )
        )
    )

    val places = listOf<DummyPlace>(
        DummyPlace(
            name = "Pizza Boy",
            options = listOf("Vegetarian", "Halal")
        ),
        DummyPlace(
            name = "Cafe 101",
            options = listOf("Vegan")
        ),
        DummyPlace(
            name = "Robinson's BBQ",
            options = listOf("Halal")
        ),
        DummyPlace(
            name = "WackDonald's",
            options = listOf("Gluten-Free", "Vegetarian")
        ),
        DummyPlace(
            name = "Stan's Meat Shack",
            options = listOf("Halal")
        ),
        DummyPlace(
            name = "Freshburg",
            options = listOf("Vegetarian", "Halal", "Kosher")
        )
    )
}