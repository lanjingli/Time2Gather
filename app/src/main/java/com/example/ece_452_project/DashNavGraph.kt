package com.example.ece_452_project

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ece_452_project.ui.DashboardScreen
import com.example.ece_452_project.ui.InfoViewModel

enum class DashScreen(){
    Dashboard,
}

/*TODO: create DashViewModel for this graph to use instead*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashNavGraph(
    viewModel: InfoViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = DashScreen.Dashboard.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = DashScreen.Dashboard.name){
                DashboardScreen()
            }
        }
    }
}