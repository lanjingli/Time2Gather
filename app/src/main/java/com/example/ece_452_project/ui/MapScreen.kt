package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.DummyPlace
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * Composable for the initial location selection screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    locations: List<DummyPlace>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hello),
            style = MaterialTheme.typography.headlineLarge
        )
        val waterloo = LatLng(43.472646, -80.537666)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(waterloo, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
        {
//            DummyData.places.forEach { place ->
//                Marker(
//                    MarkerState(position = LatLng(place.latitude, place.longitude)),
//                    place.name,
//                    "Marker"
//                )
//            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MapPreview(){
//    MapScreen(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp),
//        locations = listOf<String>("Location 1", "Location 2", "Location 3"))
//}