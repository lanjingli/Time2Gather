package com.example.ece_452_project

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
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.DashViewModel
import com.example.ece_452_project.ui.DashboardScreen
import com.example.ece_452_project.ui.EventInfoScreen
import com.example.ece_452_project.ui.ListSelectScreen
import com.example.ece_452_project.ui.ScheduleScreen
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle

enum class DashScreen(){
    Dashboard,
    FriendSelect,
    TimePlaceSelect,
    Map,
    Schedule,
    ViewSchedule
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
    Scaffold(){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = DashScreen.Dashboard.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = DashScreen.Dashboard.name){
                DashboardScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    user = uiState.user,
                    onNewEventButtonClicked = {
                        navController.navigate(DashScreen.FriendSelect.name)
                    },
                    onViewScheduleButtonClicked = {
                        navController.navigate(DashScreen.ViewSchedule.name)
                    }
                )
            }
            composable(route = DashScreen.FriendSelect.name){
                val friends = DummyData.users.filter {it.username != uiState.user.username}
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
                    onTimeButtonClicked = {navController.navigate(DashScreen.Schedule.name)},
                    onPlaceButtonClicked = {navController.navigate(DashScreen.Map.name)},
                    onFinishButtonClicked = {
                        var user = uiState.user
                        user.schedule.add(uiState.selectedEvent)
                        viewModel.updateUser(user)
                        navController.navigate(DashScreen.Dashboard.name)
                    }
                )
            }
            composable(route = DashScreen.Schedule.name){
                val options = DummyData.times.map{it.format(DateTimeFormatter.ofLocalizedDateTime(
                    FormatStyle.MEDIUM))}
                ListSelectScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    title = "Times",
                    exclusive = true,
                    options = options,
                    onNextButtonClicked = {
                        val selected = DummyData.times.filterIndexed {index, _ -> it[index]}
                        viewModel.updateSelectedTime(selected[0], selected[0].plusHours(1))
                        navController.navigate(DashScreen.TimePlaceSelect.name)
                    }
                )
            }
            composable(route = DashScreen.Map.name){
                val options = DummyData.places.map{it.name + " - " + it.options.joinToString ( ", " )}
                ListSelectScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    title = "Locations",
                    exclusive = true,
                    options = options,
                    onNextButtonClicked = {
                        val selected = DummyData.places.filterIndexed {index, _ -> it[index]}
                        viewModel.updateSelectedPlace(selected[0].name)
                        navController.navigate(DashScreen.TimePlaceSelect.name)
                    }
                )
            }
            composable(route = DashScreen.ViewSchedule.name){
                ScheduleScreen(modifier = Modifier.fillMaxSize().padding(16.dp))
            }
        }
    }
}