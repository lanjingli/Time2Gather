package com.example.ece_452_project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.EventViewModel
import com.example.ece_452_project.ui.EventConfigScreen
import com.example.ece_452_project.ui.EventPreferencesScreen
import com.example.ece_452_project.ui.InviteFriendsScreen

enum class EventScreen(){
    EventConfig,
    EventPreferences,
    InviteFriends,
    Dashboard
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventNavGraph(
    viewModel: EventViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    user: User = User(),
    event: Event = Event()
) {
    viewModel.updateUser(user)
    viewModel.updateEvent(event)
    Scaffold(){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = EventScreen.EventConfig.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = EventScreen.EventConfig.name){
                EventConfigScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    user = uiState.user,
                    event = uiState.event,
                    eventNameText = viewModel.eventName,
                    eventDescText = viewModel.eventDesc,
                    decisionDeadlineText = viewModel.decisionDeadline,
                    timeToAllocText = viewModel.timeToAlloc,
                    startDateText = viewModel.startDate,
                    endDateText = viewModel.endDate,
                    onEventNameChange = { viewModel.updateEventName(it) },
                    onEventDescChange = { viewModel.updateEventDesc(it) },
                    onDecisionDeadlineChange = { viewModel.updateDecisionDeadline(it) },
                    onTimeToAllocChange = { viewModel.updateTimeToAlloc(it) },
                    onStartDateChange = { viewModel.updateStartDate(it) },
                    onInviteFriendsClick = {
                        viewModel.createEventFromInputs(user)
                        navController.navigate(EventScreen.InviteFriends.name)
                    },
                    onSaveEventSettingClick = {
                        viewModel.createEventFromInputs(user)
                        navController.navigate(EventScreen.EventPreferences.name)
                    }
                )
            }
            composable(route = EventScreen.EventPreferences.name){
                EventPreferencesScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    user = uiState.user,
                    onBackToSettingClick = { navController.navigate(EventScreen.EventConfig.name) },
                    // Go to ScheduleScreen
                    onEditEventTimePreferencesClick = { },
                    // Go to MapScreen
                    onEditEventLocationPreferencesClick = { },
                    onSaveEventPreferencesClick = { navController.navigate(EventScreen.Dashboard.name) }
                )
            }
            composable(route = EventScreen.InviteFriends.name){
                InviteFriendsScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    user = uiState.user,
                    checkFriendStates = viewModel.invited,
//                    onCheckFriendChange = {user: User, new: Boolean, i: Int -> viewModel.updateInvitedList(user, new, i)},
                    onCheckFriendChange = {},
                    onInviteClick = {
                        viewModel.updateEventAfterInvite(user)
                        navController.navigate(EventScreen.EventConfig.name)
                    }
                )
            }
            composable(route = EventScreen.Dashboard.name) {
                DashNavGraph(
                    user = uiState.user
                )
            }
        }
    }
}