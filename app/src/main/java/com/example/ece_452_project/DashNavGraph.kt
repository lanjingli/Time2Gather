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
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.DashViewModel
import com.example.ece_452_project.ui.DashboardScreen

enum class DashScreen(){
    Dashboard,
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
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    user = uiState.user
                )
            }
        }
    }
}