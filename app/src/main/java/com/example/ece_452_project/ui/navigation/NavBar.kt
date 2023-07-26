package com.example.ece_452_project.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ece_452_project.DashScreen
import com.example.ece_452_project.data.User
import com.example.ece_452_project.remote.FirestoreUtils
import com.example.ece_452_project.ui.DashViewModel

@Composable
fun AppNavigationBar(
    viewModel: DashViewModel,
    navController: NavController,
    user: User
) {

    val navItems = listOf(NavBarItem.Home, NavBarItem.Calendar, NavBarItem.Friends, NavBarItem.Settings)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val uiState by viewModel.uiState.collectAsState()

        navItems.forEach { item ->

            NavigationBarItem(
                icon = {Icon(item.icon, item.title)},
                label = { Text(text = item.title, fontSize = 9.sp) },
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any {it.route == item.route } == true,
                onClick = {
                    if (item.route != "FriendDashboard") {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    } else {
                        FirestoreUtils.getUserFriends(uiState.user, mutableListOf<User>()) { userList ->
                            var friends = mutableListOf<User>();
                            for (user in userList) {
                                friends.add(User(user))
                            }
                            viewModel.updateSelectedFriends(friends)
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                }
            )
        }
    }
}