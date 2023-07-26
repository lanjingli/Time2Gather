package com.example.ece_452_project

import android.util.Log
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
import com.example.ece_452_project.data.MenuData
import com.example.ece_452_project.remote.FirestoreUtils
import com.example.ece_452_project.remote.RemoteUser
import com.example.ece_452_project.ui.InfoViewModel
import com.example.ece_452_project.ui.LoginScreen
import com.example.ece_452_project.ui.PreferencesScreen
import com.example.ece_452_project.ui.RegisterScreen
import com.example.ece_452_project.ui.ReorderableList
import com.example.ece_452_project.ui.navigation.AppNavigationBar
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

enum class InfoScreen(){
    Login,
    Register,
    Preferences,
    Dashboard
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InfoNavGraph(
    viewModel: InfoViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
    ){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = InfoScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = InfoScreen.Login.name) {
                LoginScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    onLoginButtonClicked = {
                        var auth = FirestoreUtils.auth()
                        if (viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty()) {
                            auth.signInWithEmailAndPassword(viewModel.email, viewModel.password)
                                .addOnCompleteListener() { task ->
                                    if (task.isSuccessful) {
                                        FirestoreUtils.getCurrentUser {
                                            viewModel.updateUser(it)
                                            navController.navigate(InfoScreen.Dashboard.name)
                                        }
                                    } else {
                                        viewModel.updateDialog(true)
                                        Log.d("myTag", task.exception.toString())
                                    }
                                }
                            }
                        else {
                            viewModel.updateDialog(true)
                        }
                        //viewModel.fetchDummyUser()
                    },
                    onSignupButtonClicked = { navController.navigate(InfoScreen.Register.name) },
                    emailText = viewModel.email,
                    passwordText = viewModel.password,
                    onEmailChange = { viewModel.updateEmail(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    openDialog = viewModel.openDialog,
                    onDialogDismiss = { viewModel.updateDialog(false) }
                )
            }
            composable(route = InfoScreen.Register.name) {
                RegisterScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    emailText = viewModel.email,
                    passwordText = viewModel.password,
                    cpasswordText = viewModel.cpassword,
                    onEmailChange = { viewModel.updateEmail(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    onCPasswordChange = { viewModel.updateCPassword(it) },
                    onSignupButtonClicked = {
                        var auth = FirestoreUtils.auth()
                        if (viewModel.email.isEmpty() || viewModel.password.isEmpty()){
                            viewModel.updateDialogText("Please Enter Values for Email and Password")
                            viewModel.updateDialog(true)
                        }
                        else if (viewModel.password != viewModel.cpassword){
                            viewModel.updateDialogText("Passwords Do Not Match")
                            viewModel.updateDialog(true)
                        }
                        else {
                            auth.createUserWithEmailAndPassword(viewModel.email, viewModel.password)
                                .addOnCompleteListener() { task ->
                                    if (task.isSuccessful) {
                                        navController.navigate(InfoScreen.Preferences.name)
                                    } else {
                                        viewModel.updateDialog(true)
                                        if (task.exception is FirebaseAuthUserCollisionException){
                                            viewModel.updateDialogText("A User with that Email Already Exists")
                                        }
                                        else if (task.exception is FirebaseAuthWeakPasswordException){
                                            viewModel.updateDialogText("Password Must be at Least 6 Characters Long")
                                        }
                                    }
                                }
                        }
                    },
                    openDialog = viewModel.openDialog,
                    onDialogDismiss = { viewModel.updateDialog(false) },
                    dialogText = viewModel.dialogText
                )
            }
            composable(route = InfoScreen.Preferences.name) {
                PreferencesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    nameText = viewModel.name,
                    usernameText = viewModel.username,
                    onNameChange = { viewModel.updateName(it) },
                    onUsernameChange = { viewModel.updateUsername(it) },
                    checkboxStates = viewModel.dietary,
                    onCheckboxChange = {new: Boolean, i: Int -> viewModel.updateDietary(new, i)},
                    onSaveButtonClicked = {
                        if (viewModel.username.isEmpty()){
                            viewModel.updateDialog(true)
                        }
                        else {
                            FirestoreUtils.usernameExists(viewModel.username) {
                                if (!it){
                                    val auth = FirestoreUtils.auth()
                                    val firestore = FirestoreUtils.firestore()
                                    viewModel.updateUserFromInputs()
                                    // re-update for remote
                                    val tmp_dietary = mutableListOf<String>()
                                    viewModel.dietary.forEachIndexed(){i,it->
                                        if (it) tmp_dietary.add(MenuData.dietaryOptions[i])
                                    }
                                    val userData = RemoteUser(
                                        username = viewModel.username,
                                        email = viewModel.email,
                                        password = viewModel.password,
                                        name = viewModel.name,
                                        dietary = tmp_dietary
                                    )
                                    auth.currentUser?.let{authUser ->
                                        firestore.collection("users").document(authUser.uid)
                                            .set(userData)
                                        navController.navigate(InfoScreen.Dashboard.name)
                                    }
                                }
                                else{
                                    viewModel.updateDialog(true)
                                }
                            }
                        }
                    },
                    openDialog = viewModel.openDialog,
                    onDialogDismiss = { viewModel.updateDialog(false) }
                )
            }
            composable(route = InfoScreen.Dashboard.name) {
                DashNavGraph(
                    user = uiState.user
                )
            }
        }
    }
}