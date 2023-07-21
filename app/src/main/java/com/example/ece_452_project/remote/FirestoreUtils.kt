package com.example.ece_452_project.remote

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// set to true if using localhost, false if using real remote database
private val USE_EMULATORS = true

object FirestoreUtils{
    private var firestore : FirebaseFirestore
    private var auth : FirebaseAuth

    init {
        firestore = firestoreInit()
        auth = authInit()
    }

    private fun firestoreInit(): FirebaseFirestore {
        val firestore = Firebase.firestore
        if (USE_EMULATORS) {
            firestore.useEmulator("10.0.2.2", 8080)
        }
        return firestore
    }

    fun firestore(): FirebaseFirestore{
        return firestore
    }

    private fun authInit(): FirebaseAuth {
        val firebaseAuth = Firebase.auth
        if (USE_EMULATORS) {
            firebaseAuth.useEmulator("10.0.2.2", 9099)
        }
        return firebaseAuth
    }

    fun auth(): FirebaseAuth {
        return auth
    }

    fun usernameExists(username: String, listener: (exists: Boolean) -> Unit) {
        firestore.collection("users").whereEqualTo(RemoteUser.FIELD_USERNAME, username).get()
            .addOnSuccessListener {
                listener(!it.isEmpty)
            }
    }

    fun getCurrentUser(listener: (user: User) -> Unit) {
        val result = User()
        auth.currentUser?.let{authUser ->
            firestore.collection("users").document(authUser.uid).get()
                .addOnSuccessListener {
                    result.username = it.data?.getValue(RemoteUser.FIELD_USERNAME) as String
                    result.email = it.data?.getValue(RemoteUser.FIELD_EMAIL) as String
                    result.name = it.data?.getValue(RemoteUser.FIELD_NAME) as String
                    result.password = it.data?.getValue(RemoteUser.FIELD_PASSWORD) as String
                    result.dietary = it.data?.getValue(RemoteUser.FIELD_DIETARY) as List<String>
                    firestore.collection("events").whereArrayContains(RemoteEvent.FIELD_USERS, result.username)
                        .get().addOnSuccessListener { query ->
                            query.documents.forEach {itevent->
                                val rEvent = RemoteEvent(
                                    name = itevent.data?.getValue(RemoteEvent.FIELD_NAME) as String,
                                    description = itevent.data?.getValue(RemoteEvent.FIELD_DESCRIPTION) as String,
                                    location = itevent.data?.getValue(RemoteEvent.FIELD_LOCATION) as String,
                                    users = itevent.data?.getValue(RemoteEvent.FIELD_USERS) as List<String>,
                                    isShared = itevent.data?.getValue(RemoteEvent.FIELD_SHARED) as Boolean,
                                    start = itevent.data?.getValue(RemoteEvent.FIELD_START) as Timestamp,
                                    end = itevent.data?.getValue(RemoteEvent.FIELD_END) as Timestamp,
                                )
                                result.schedule.add(Event(rEvent))
                            }
                        }
                    listener(result)
                }
        }
    }

    fun resetDummyUsers() {
        DummyData.users.forEach { user ->
            // sign in / create users in auth and add data to firestore
            val userData = RemoteUser(user)
            auth.createUserWithEmailAndPassword(user.email, user.password)
            auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener() {result ->
                    result.user?.let{
                        firestore.collection("users").document(it.uid)
                            .set(userData)
                    }
                }
        }
        auth.signOut()
    }

    fun resetDummyEvents(){
        var count = 0
        DummyData.users.forEach { user ->
            user.schedule.forEach { event ->
                val eventData = RemoteEvent(event, user)
                firestore.collection("events").document("dummy-$count")
                    .set(eventData)
                count += 1
            }
        }
    }
}