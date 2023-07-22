package com.example.ece_452_project.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ece_452_project.R

sealed class NavBarItem (var title:String, var icon: ImageVector, var route:String){

    object Home : NavBarItem("Home", Icons.Default.Home, "Home")
    object Calendar : NavBarItem("Calendar", Icons.Default.DateRange, "CalendarView")
    object Friends : NavBarItem("Friends", Icons.Default.Person, "FriendDashboard")
    object Settings : NavBarItem("Settings", Icons.Default.Settings, "Settings")

}
