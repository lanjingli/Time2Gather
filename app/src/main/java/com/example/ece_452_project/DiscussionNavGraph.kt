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
import com.example.ece_452_project.data.Discussion
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.DashViewModel
import com.example.ece_452_project.ui.DiscussionOptionScreen
import com.example.ece_452_project.ui.DiscussionViewModel
import com.example.ece_452_project.ui.MapScreen
import com.example.ece_452_project.ui.MapViewModel

enum class DiscussionOptionScreen(){
    Start,
    End
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DiscussionNavGraph(
    viewModel: DiscussionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    user: User = User(),
    discussion: Discussion = Discussion()
) {
    viewModel.updateUser(user)
    viewModel.updateSelectedDiscussion(discussion)
    Scaffold(){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = DiscussionOptionScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = DiscussionOptionScreen.Start.name){
                DiscussionOptionScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    user = uiState.user,
                    discussion = uiState.selectedDiscussion,
                    onTimeButtonClicked = {},
                    onPlaceButtonClicked = {},
                    onFinishButtonClicked = {
                        navController.navigate(DiscussionOptionScreen.End.name)
                        //navController.navigate(DashScreen.EventFinal.name)
                    }
                )
            }
            composable(route = DiscussionOptionScreen.End.name){
                DashNavGraph(
                    user = uiState.user
                )
            }
        }
    }
}