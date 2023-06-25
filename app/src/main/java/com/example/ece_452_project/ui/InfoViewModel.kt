package com.example.ece_452_project.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ece_452_project.data.InfoUiState
import com.example.ece_452_project.data.MenuData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
}