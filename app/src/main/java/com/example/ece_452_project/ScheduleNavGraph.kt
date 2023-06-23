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
import com.example.ece_452_project.ui.ScheduleScreen
import com.example.ece_452_project.ui.ScheduleViewModel

enum class ScheduleScreen(){
    Start,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleNavGraph(
    viewModel: ScheduleViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ScheduleScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = ScheduleScreen.Start.name){
                ScheduleScreen(modifier = Modifier.fillMaxSize().padding(16.dp))
            }
        }
    }
}