package com.example.ece_452_project.ui

import com.example.ece_452_project.data.DummyPlace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
/**
 * Composable for the initial location selection screen
 */
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    locations: List<DummyPlace>
) {

    // not sure if this is right. I also tried just 'locationFragment().LocationScreen()'
    val locationFragment = remember { LocationFragment() }
    locationFragment.LocationScreen()
}

