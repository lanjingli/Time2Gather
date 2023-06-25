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
            friends = mutableListOf(
                "bravo",
                "charlie",
                "delta"
            ),
            schedule = mutableListOf(
                Event(
                    name = "Trip to Pizza Boy",
                    start = LocalDateTime.of(2023, 6, 26, 8, 30),
                    end = LocalDateTime.of(2023, 6, 26, 16, 30)
                ),
                Event(
                    name = "Meeting at Cafe 101",
                    start = LocalDateTime.of(2023, 6, 27, 8, 30),
                    end = LocalDateTime.of(2023, 6, 27, 16, 30)
                ),
                Event(
                    name = "Hangout at Cafe 202",
                    start = LocalDateTime.of(2023, 6, 28, 8, 30),
                    end = LocalDateTime.of(2023, 6, 28, 16, 30)
                ),
                Event(
                    name = "Meeting at Cafe 303",
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
            friends = mutableListOf(
                "alpha",
                "delta"
            ),
            dietary = listOf<String>(),
            schedule = mutableListOf(
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
            friends = mutableListOf(
                "bravo",
                "alpha",
                "delta"
            ),
            dietary = listOf<String>("Vegetarian"),
            schedule = mutableListOf(
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
            friends = mutableListOf(
                "bravo"
            ),
            dietary = listOf<String>(""),
            schedule = mutableListOf(
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