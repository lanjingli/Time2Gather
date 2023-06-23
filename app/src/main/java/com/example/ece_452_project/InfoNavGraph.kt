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
import com.example.ece_452_project.ui.InfoViewModel
import com.example.ece_452_project.ui.LoginScreen
import com.example.ece_452_project.ui.PreferencesScreen
import com.example.ece_452_project.ui.RegisterScreen

enum class InfoScreen(){
    Login,
    Register,
    Preferences,
    Dashboard
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoNavGraph(
    viewModel: InfoViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = InfoScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = InfoScreen.Login.name) {
                LoginScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    onLoginButtonClicked = { navController.navigate(InfoScreen.Dashboard.name) },
                    onSignupButtonClicked = { navController.navigate(InfoScreen.Register.name) },
                    usernameText = viewModel.username,
                    passwordText = viewModel.password,
                    onUsernameChange = { viewModel.updateUsername(it) },
                    onPasswordChange = { viewModel.updatePassword(it) }
                )
            }
            composable(route = InfoScreen.Register.name) {
                RegisterScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    usernameText = viewModel.username,
                    passwordText = viewModel.password,
                    cpasswordText = viewModel.cpassword,
                    onUsernameChange = { viewModel.updateUsername(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    onCPasswordChange = { viewModel.updateCPassword(it) },
                    onSignupButtonClicked = {navController.navigate(InfoScreen.Preferences.name)}
                )
            }
            composable(route = InfoScreen.Preferences.name) {
                PreferencesScreen(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    nameText = viewModel.name,
                    emailText = viewModel.email,
                    onNameChange = { viewModel.updateName(it) },
                    onEmailChange = { viewModel.updateEmail(it) },
                    checkboxStates = viewModel.dietary,
                    onCheckboxChange = {new: Boolean, i: Int -> viewModel.updateDietary(new, i)},
                    onSaveButtonClicked = {navController.navigate(InfoScreen.Dashboard.name)}
                )
            }
            composable(route = InfoScreen.Dashboard.name) {
                DashNavGraph(

                )
            }
        }
    }
}