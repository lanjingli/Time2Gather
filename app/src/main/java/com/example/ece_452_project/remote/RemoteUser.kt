package com.example.ece_452_project.remote

import com.example.ece_452_project.data.User
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class RemoteUser (
    var email : String? = null,
    var password : String? = null,
    var username: String? = null,
    var name : String? = null,
    var dietary : List<String>? = null,
    var friends: List<String>? = null
) {
    constructor(user: User) : this() {
        email = user.email
        password = user.password
        username = user.username
        name = user.name
        dietary = user.dietary
        friends = user.friends
    }

    companion object {
        const val FIELD_USERNAME = "username"
        const val FIELD_PASSWORD = "password"
        const val FIELD_NAME = "name"
        const val FIELD_EMAIL = "email"
        const val FIELD_DIETARY = "dietary"
        const val FIELD_FRIENDS = "friends"
    }
}

