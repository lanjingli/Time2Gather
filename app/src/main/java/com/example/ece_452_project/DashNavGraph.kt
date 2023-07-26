package com.example.ece_452_project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ece_452_project.data.Discussion
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import com.example.ece_452_project.remote.FirestoreUtils
import com.example.ece_452_project.ui.CalendarMonthlyScreen
import com.example.ece_452_project.ui.DashViewModel
import com.example.ece_452_project.ui.DashboardScreen
import com.example.ece_452_project.ui.EventFinalScreen
import com.example.ece_452_project.ui.EventInfoScreen
import com.example.ece_452_project.ui.DiscussionOptionScreen
import com.example.ece_452_project.ui.EventSettingScreen
import com.example.ece_452_project.ui.FriendMainScreen
import com.example.ece_452_project.ui.ListSelectScreen
import com.example.ece_452_project.ui.MapScreen
import com.example.ece_452_project.ui.TimeSelectionScreen
import com.example.ece_452_project.ui.navigation.AppNavigationBar
import com.example.ece_452_project.ui.navigation.NavBarItem
import com.google.firebase.firestore.FieldValue

enum class DashScreen(){
    Dashboard,
    EventSetting,
    FriendSelect,
    TimePlaceSelect,
    Map,
    Schedule,
    DiscussionOption,
    EventFinal
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DashNavGraph(
    viewModel: DashViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    user: User = User()
) {
    viewModel.updateUser(user)
    Scaffold(
        bottomBar = { AppNavigationBar(navController = navController) }
    ){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = NavBarItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = NavBarItem.Home.route){
                DashboardScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    user = uiState.user,
                    onNewEventButtonClicked = {
                        navController.navigate(DashScreen.EventSetting.name)
                    },
                    onDiscussionClick = {
                        viewModel.updateSelectedDiscussion(it)
                        navController.navigate(DashScreen.DiscussionOption.name)
                    }
                )
            }
            composable(route = DashScreen.EventSetting.name){
                EventSettingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    user = uiState.user,
                    eventNameText = viewModel.eventName,
                    eventDescText = viewModel.eventDesc,
                    onEventNameChange = { viewModel.updateEventName(it) },
                    onEventDescChange = { viewModel.updateEventDescription(it) },
                    onInviteFriendClicked = {
                        viewModel.updateEventSetting()
                        navController.navigate(DashScreen.FriendSelect.name)
                    }
                )
            }
            composable(route = DashScreen.DiscussionOption.name){
                DiscussionNavGraph(
                    user = uiState.user,
                    discussion = uiState.selectedDiscussion)
            }
            composable(route = DashScreen.EventFinal.name){
                EventFinalScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    user = uiState.user,
                    event = uiState.selectedEvent,
                    friends = uiState.selectedFriends,
                    onFinishButtonClicked = {
                        if(it) {
                            viewModel.updateEventAttend(user.username)
                            viewModel.updateUserSchedule(uiState.selectedEvent)
                            val db = FirestoreUtils.firestore()
                            val newEventRef = db.collection("events").document(uiState.selectedEvent.id)
                            newEventRef.update("attend", FieldValue.arrayUnion(user.username))
                            viewModel.updateUser(user)
                        }
                        navController.navigate(NavBarItem.Home.route)
                    },
                    onDoneButtonClicked = {
                        navController.navigate(NavBarItem.Home.route)
                    }
                )
            }
            composable(route = DashScreen.Schedule.name){
                TimeSelectionScreen(
                    currentUser = uiState.user,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    friends = uiState.selectedFriends,
                    onBackToEventInfoClicked = {navController.navigate(DashScreen.TimePlaceSelect.name)},
                    onDoneButtonClicked = {
                        viewModel.updateSelectedTime(it[0], it[1])
                        navController.navigate(DashScreen.TimePlaceSelect.name)
                    }
                )
            }
            composable(route = DashScreen.FriendSelect.name){
                val friends = DummyData.users.filter {it.email != uiState.user.email}
                val options = friends.map {it.name + " - " + it.dietary.joinToString( ", " )}
                ListSelectScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    title = "Friends",
                    options = options,
                    onNextButtonClicked = {
                        val selected = friends.filterIndexed {index, _ -> it[index]}
                        viewModel.updateSelectedFriends(selected)
                        navController.navigate(DashScreen.TimePlaceSelect.name)
                    }
                )
            }
            composable(route = DashScreen.TimePlaceSelect.name){
                EventInfoScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    onBackToFriendsClicked = {navController.navigate(DashScreen.FriendSelect.name)},
                    onTimeButtonClicked = {navController.navigate(DashScreen.Schedule.name)},
                    onPlaceButtonClicked = {navController.navigate(DashScreen.Map.name)},
                    onDeadlineChange = {viewModel.updateDeadlineField(it)},
                    onFinishButtonClicked = {
                        val user = uiState.user
                        viewModel.updateDeadlineDate(it)
                        // Add new discussion to db
                        var listUser = mutableListOf<String>(user.username)
                        uiState.selectedFriends.forEach { user ->
                            listUser.add(user.username)
                        }
                        val data = Discussion(uiState.selectedEvent)
                        data.users = listUser

                        // BASED ON CURRENT LOGIC:
                        // initialize the list of lists to just one 0
                        // bc theres only one option.
                        // but change this if its not always going to be just one option

                        data.rankings = mutableListOf(listOf(0))
                        FirestoreUtils.addDiscussion(data, {})
                        user.discussions.add(data)
                        viewModel.updateUser(user)
                        viewModel.resetSelectedEvent()
                        navController.navigate(NavBarItem.Home.route)
                    }
                )
            }
            composable(route = DashScreen.Map.name){
                val options = DummyData.places.map{it.name + " - " + it.options.joinToString ( ", " )}
                Column() {
                    ListSelectScreen(
                        modifier = Modifier
//                        .fillMaxSize()
                            .padding(16.dp),
                        title = "Locations",
                        exclusive = true,
                        options = options,
                        onNextButtonClicked = {
                            val selected = DummyData.places.filterIndexed {index, _ -> it[index]}
                            if (selected.isNotEmpty()) viewModel.updateSelectedPlace(selected[0].name)
                            navController.navigate(DashScreen.TimePlaceSelect.name)
                        }
                    )
                    MapScreen(modifier =
                    Modifier
//                        .fillMaxSize()
                        .padding(16.dp),
                        locations = DummyData.places)
                }
            }
            composable(route = NavBarItem.Calendar.route){
                CalendarMonthlyScreen(user = uiState.user)
            }
            composable(route = NavBarItem.Friends.route){
                FriendMainScreen()
            }
//            composable(route = NavBarItem.Settings.route){
//                PreferencesScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    nameText = viewModel.name,
//                    usernameText = viewModel.username,
//                    onNameChange = { viewModel.updateName(it) },
//                    onUsernameChange = { viewModel.updateUsername(it) },
//                    checkboxStates = viewModel.dietary,
//                    onCheckboxChange = {new: Boolean, i: Int -> viewModel.updateDietary(new, i)},
//                    onSaveButtonClicked = {
//                        if (viewModel.username.isEmpty()){
//                            viewModel.updateDialog(true)
//                        }
//                        else {
//                            FirestoreUtils.usernameExists(viewModel.username) {
//                                if (!it){
//                                    val auth = FirestoreUtils.auth()
//                                    val firestore = FirestoreUtils.firestore()
//                                    viewModel.updateUserFromInputs()
//                                    // re-update for remote
//                                    val tmp_dietary = mutableListOf<String>()
//                                    viewModel.dietary.forEachIndexed(){i,it->
//                                        if (it) tmp_dietary.add(MenuData.dietaryOptions[i])
//                                    }
//                                    val userData = RemoteUser(
//                                        username = viewModel.username,
//                                        email = viewModel.email,
//                                        password = viewModel.password,
//                                        name = viewModel.name,
//                                        dietary = tmp_dietary
//                                    )
//                                    auth.currentUser?.let{authUser ->
//                                        firestore.collection("users").document(authUser.uid)
//                                            .set(userData)
//                                        navController.navigate(InfoScreen.Dashboard.name)
//                                    }
//                                }
//                                else{
//                                    viewModel.updateDialog(true)
//                                }
//                            }
//                        }
//                    },
//                    openDialog = viewModel.openDialog,
//                    onDialogDismiss = { viewModel.updateDialog(false) }
//                )
//            }
        }
    }
}