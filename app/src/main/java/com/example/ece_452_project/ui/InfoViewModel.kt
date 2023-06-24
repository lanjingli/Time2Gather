package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.InfoUiState
import com.example.ece_452_project.data.MenuData
import com.example.ece_452_project.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InfoUiState())
    val uiState: StateFlow<InfoUiState> = _uiState.asStateFlow()

    // Hold text field and checkbox values
    var dietary by mutableStateOf(List<Boolean>(MenuData.dietaryOptions.size) {false})
        private set
    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var cpassword by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    // Update input values
    fun updateUsername(value: String) { username = value }
    fun updatePassword(value: String) { password = value }
    fun updateCPassword(value: String) { cpassword = value }
    fun updateName(value: String) { name = value }
    fun updateEmail(value: String) { email = value }
    fun updateDietary(value: Boolean, i: Int) {
        if (i >= 0 && i < MenuData.dietaryOptions.size) {
            var temp = dietary.toMutableList()
            temp[i] = value
            dietary = temp.toList()
        }
    }

    fun resetInfo(){
        _uiState.value = InfoUiState()
    }

    fun updateUserFromInputs(){
        val tmp_dietary = mutableListOf<String>()
        dietary.forEachIndexed(){i,it->
            if (it) tmp_dietary.add(MenuData.dietaryOptions[i])
        }
        val tmp_user = User(
            username = username,
            password = password,
            name = name,
            email = email,
            dietary = tmp_dietary
        )
        _uiState.update { currentState ->
            currentState.copy(user = tmp_user)
        }
    }

    fun updateUser(user: User){
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun fetchDummyUser(){
        DummyData.users.forEach{it->
            if (it.username == username) {
                updateUser(it)
            }
        }
    }
}