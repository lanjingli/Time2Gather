package com.example.ece_452_project.ui

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.fragment.app.viewModels
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.ece_452_project.BuildConfig
import com.example.ece_452_project.R
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class LocationFragment : Fragment() {

    private val viewModel by viewModels<LocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LocationScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        Places.initialize(requireContext().applicationContext, BuildConfig.MAPS_API_KEY)
        viewModel.placesClient = Places.createClient(requireContext())
        viewModel.geoCoder = Geocoder(requireContext())
    }

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalAnimationApi::class)
    @Composable
    fun LocationScreen(modifier: Modifier = Modifier) {
        val locationPermissionState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        LaunchedEffect(locationPermissionState.allPermissionsGranted) {
            if (locationPermissionState.allPermissionsGranted) {
                if (locationEnabled()) {
                    viewModel.getCurrentLocation()
                } else {
                    viewModel.locationState = LocationState.LocationDisabled
                }
            }
        }

        AnimatedContent(
            viewModel.locationState
        ) { state ->
            when (state) {
                is LocationState.NoPermission -> {
                    Column {
                        Text("We need location permission to continue")
                        Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                            Text("Request permission")
                        }
                    }
                }

                is LocationState.LocationDisabled -> {
                    Column {
                        Text("We need location to continue")
                        Button(onClick = { requestLocationEnable() }) {
                            Text("Enable location")
                        }
                    }
                }

                is LocationState.LocationLoading -> {
                    Text("Loading Map")
                }

                is LocationState.Error -> {
                    Column {
                        Text("Error fetching your location")
                        Button(onClick = { viewModel.getCurrentLocation() }) {
                            Text("Retry")
                        }
                    }
                }

                is LocationState.LocationAvailable -> {
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(state.cameraLatLang, 15f)
                    }

                    val mapUiSettings by remember { mutableStateOf(MapUiSettings()) }
                    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
                    val scope = rememberCoroutineScope()

                    LaunchedEffect(viewModel.currentLatLong) {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
                    }

                    LaunchedEffect(cameraPositionState.isMoving) {
                        if (!cameraPositionState.isMoving) {
                            viewModel.getAddress(cameraPositionState.position.target)
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        GoogleMap(modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            uiSettings = mapUiSettings,
                            properties = mapProperties,
                            onMapClick = {
                                scope.launch {
                                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(it))
                                }
                            })
//                        Icon(
//                            painter = painterResource(id = R.drawable.current_position_tennis_ball),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(24.dp)
//                                .align(Alignment.Center)
//                        )

                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(8.dp)
                                .fillMaxWidth(),
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AnimatedVisibility(
                                    viewModel.locationAutofill.isNotEmpty(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        items(viewModel.locationAutofill) {
                                            Row(modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                                ) {
                                                Text(it.address)
                                            }
                                        }
                                    }
                                    Spacer(Modifier.height(16.dp))
                                }
                                OutlinedTextField(
                                    value = viewModel.text, onValueChange = {
                                        viewModel.text = it
                                        viewModel.searchPlaces(it)
                                    }, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun locationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun requestLocationEnable() {
        activity?.let {
            val locationRequest = LocationRequest.create()
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            LocationServices.getSettingsClient(it).checkLocationSettings(builder.build())
                .addOnSuccessListener {
                    if (it.locationSettingsStates?.isLocationPresent == true) {
                        viewModel.getCurrentLocation()
                    }
                }.addOnFailureListener {
                    if (it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(requireActivity(), 999)
                        } catch (e: IntentSender.SendIntentException) {
                            e.printStackTrace()
                        }
                    }
                }

        }
    }
}