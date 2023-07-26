package com.example.ece_452_project.remote

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.example.ece_452_project.data.Discussion
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.TimePlace
import com.example.ece_452_project.data.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.ZonedDateTime

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
                    getUserEvents(result){events->
                        result.schedule = events
                        getUserDiscussions(result){discussions->
                            result.discussions = discussions
                            listener(result)
                        }
                    }
                }
        }
    }

    fun getUserEvents(user: User, listener: (events: MutableList<Event>) -> Unit){
        var schedule = mutableListOf<Event>()
        firestore.collection("events").whereArrayContains(RemoteEvent.FIELD_USERS, user.username)
            .get().addOnSuccessListener { query ->
                query.documents.forEach {itevent->
                    val rEvent = RemoteEvent(
                        id = itevent.data?.getValue(RemoteEvent.FIELD_ID) as String,
                        name = itevent.data?.getValue(RemoteEvent.FIELD_NAME) as String,
                        description = itevent.data?.getValue(RemoteEvent.FIELD_DESCRIPTION) as String,
                        location = itevent.data?.getValue(RemoteEvent.FIELD_LOCATION) as String,
                        users = itevent.data?.getValue(RemoteEvent.FIELD_USERS) as List<String>,
                        isShared = itevent.data?.getValue(RemoteEvent.FIELD_SHARED) as Boolean,
                        start = itevent.data?.getValue(RemoteEvent.FIELD_START) as Timestamp,
                        end = itevent.data?.getValue(RemoteEvent.FIELD_END) as Timestamp,
                    )
                    schedule.add(Event(rEvent))
                }
                listener(schedule)
            }
    }

    fun getUserDiscussions(user: User, listener: (events: MutableList<Discussion>) -> Unit){
        var discussions = mutableListOf<Discussion>()
        firestore.collection("discussions").whereArrayContains(RemoteDiscussion.FIELD_USERS, user.username)
            .get().addOnSuccessListener { query ->
                query.documents.forEach {itevent->
                    val rDiscuss = RemoteDiscussion(
                        id = itevent.data?.getValue(RemoteDiscussion.FIELD_ID) as String,
                        name = itevent.data?.getValue(RemoteDiscussion.FIELD_NAME) as String,
                        description = itevent.data?.getValue(RemoteDiscussion.FIELD_DESCRIPTION) as String,
                        deadline = itevent.data?.getValue(RemoteDiscussion.FIELD_DEADLINE) as Timestamp,
                        users = itevent.data?.getValue(RemoteDiscussion.FIELD_USERS) as List<String>,
                        rankings = itevent.data?.getValue(RemoteDiscussion.FIELD_RANKINGS) as List<Int>,
                    )
                    var options = mutableListOf<RemoteTimePlace>()
                    val op_hashes = itevent.data?.getValue(RemoteDiscussion.FIELD_OPTIONS) as List<HashMap<String, Object>>
                    op_hashes.forEach{hash->
                        options.add(
                            RemoteTimePlace(
                                start = hash.getValue(RemoteTimePlace.FIELD_START) as Timestamp,
                                end = hash.getValue(RemoteTimePlace.FIELD_END) as Timestamp,
                                location = hash.getValue(RemoteTimePlace.FIELD_LOCATION) as String
                            )
                        )
                    }
                    rDiscuss.options = options
                    discussions.add(Discussion(rDiscuss))
                }
                listener(discussions)
            }
    }

    fun addEvent(event: Event, listener: () -> Unit){
        val newId = firestore.collection("events").document()
        event.id = newId.id
        updateEvent(event, listener)
    }

    fun updateEvent(event: Event, listener: () -> Unit){
        firestore.collection("events").document(event.id)
            .set(RemoteEvent(event)).addOnSuccessListener {
                listener()
            }
    }

    fun getDiscussion(id: String, listener: (discussion: Discussion) -> Unit){
        firestore.collection("discussions").document(id)
            .get().addOnSuccessListener {
                val rDiscuss = RemoteDiscussion(
                    id = it.data?.getValue(RemoteDiscussion.FIELD_ID) as String,
                    name = it.data?.getValue(RemoteDiscussion.FIELD_NAME) as String,
                    description = it.data?.getValue(RemoteDiscussion.FIELD_DESCRIPTION) as String,
                    deadline = it.data?.getValue(RemoteDiscussion.FIELD_DEADLINE) as Timestamp,
                    users = it.data?.getValue(RemoteDiscussion.FIELD_USERS) as List<String>,
                    rankings = it.data?.getValue(RemoteDiscussion.FIELD_RANKINGS) as List<Int>,
                )
                var options = mutableListOf<RemoteTimePlace>()
                val op_hashes = it.data?.getValue(RemoteDiscussion.FIELD_OPTIONS) as List<HashMap<String, Object>>
                op_hashes.forEach{hash->
                    options.add(
                        RemoteTimePlace(
                            start = hash.getValue(RemoteTimePlace.FIELD_START) as Timestamp,
                            end = hash.getValue(RemoteTimePlace.FIELD_END) as Timestamp,
                            location = hash.getValue(RemoteTimePlace.FIELD_LOCATION) as String
                        )
                    )
                }
                listener(Discussion(rDiscuss))
            }
    }

    fun addDiscussion(discussion: Discussion, listener: () -> Unit){
        val newId = firestore.collection("discussions").document()
        discussion.id = newId.id
        updateDiscussion(discussion, listener)
    }

    fun updateDiscussion(discussion: Discussion, listener: () -> Unit){
        firestore.collection("discussions").document(discussion.id)
            .set(RemoteDiscussion(discussion)).addOnSuccessListener {
                listener()
            }
    }

    fun testDisc(){

        val op1 =  TimePlace(
            start = LocalDateTime.of(2023, 7, 29, 12, 30),
            end = LocalDateTime.of(2023, 7, 29, 14, 30),
            location = "Columbia Lake"
        )

        val op2 = TimePlace(
            start = LocalDateTime.of(2023, 7, 29, 12, 30),
            end = LocalDateTime.of(2023, 7, 29, 14, 30),
            location = "Waterloo Park"
        )

        val op3 = TimePlace(
            start = LocalDateTime.of(2023, 7, 30, 13, 0),
            end = LocalDateTime.of(2023, 7, 30, 15, 0),
            location = "Victoria Park"
        )

        val op4 = TimePlace(
            start = LocalDateTime.of(2023, 7, 30, 11,30 ),
            end = LocalDateTime.of(2023, 7, 30, 14, 0),
            location = "Laurel Creek"
        )

        val r = RemoteDiscussion(
            id = "test",
            name = "Bird Watching",
            description = "Watching Birds",
            deadline = Timestamp(LocalDateTime.of(2023, 7, 26, 17,0 ).toEpochSecond(ZonedDateTime.now().offset), 0),
            users = listOf("alpha", "bravo"),
            options = listOf(
                RemoteTimePlace(op1),
                RemoteTimePlace(op2),
                RemoteTimePlace(op3),
                RemoteTimePlace(op4)
            ),
            rankings = listOf(Discussion.UNRANKED, Discussion.UNRANKED)
        )
        firestore.collection("discussions").document("test")
            .set(r)
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
                var eventData = RemoteEvent(event, user)
                eventData.id = "dummy-$count"
                firestore.collection("events").document("dummy-$count")
                    .set(eventData)
                count += 1
            }
        }
    }
}