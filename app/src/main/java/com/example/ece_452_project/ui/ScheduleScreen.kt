package com.example.ece_452_project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R

/**
 * Composable for the time selection screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onPersonalButtonClicked: () -> Unit,
    onSharedButtonClicked: () -> Unit,
    onAddScheduleButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // My Schedules section
        Column(Modifier.weight((1f)).fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.SpaceEvenly){
            Text(
                text = stringResource(R.string.my_schedules),
                style = MaterialTheme.typography.headlineLarge
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPersonalButtonClicked
            ) {
                Text(
                    text = stringResource(R.string.personal),
                    fontSize = 16.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSharedButtonClicked
            ) {
                Text(
                    text = stringResource(R.string.shared),
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = onAddScheduleButtonClicked,
                shape = CircleShape
            ) {
                Text(
                    text = stringResource(R.string.plus),
                    fontSize = 16.sp
                )
            }
        }

        // Friends section
        Column(Modifier.weight((1f)).fillMaxSize().padding(15.dp)){
            Text(
                text = stringResource(R.string.friends),
                style = MaterialTheme.typography.headlineLarge
            )

        }

        // Updates section
        Column(Modifier.weight((1f)).fillMaxSize().padding(15.dp)){
            Text(
                text = stringResource(R.string.updates),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SchedulePreview(){
    ScheduleScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        onPersonalButtonClicked = {},
        onSharedButtonClicked = {},
        onAddScheduleButtonClicked = {}
    )
}
