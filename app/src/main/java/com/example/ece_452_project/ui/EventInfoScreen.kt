package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ece_452_project.DashScreen
import com.example.ece_452_project.R

@Composable
fun EventInfoScreen(
    modifier: Modifier = Modifier,
    onTimeButtonClicked: () -> Unit,
    onPlaceButtonClicked: () -> Unit,
    onFinishButtonClicked: () -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.event_details),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Step 1: Select a time",
            style = MaterialTheme.typography.labelLarge
        )
        Button(modifier = Modifier
            //.fillMaxWidth()
            .wrapContentHeight(),
            onClick = onTimeButtonClicked
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Sharp.DateRange, contentDescription = "Select Time")
                Text(
                        modifier = Modifier.padding(4.dp),
                        text = stringResource(R.string.select_time)
                    )
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Step 2: Select a place",
            style = MaterialTheme.typography.labelLarge
        )
        Button(modifier = Modifier
            //.fillMaxWidth()
            .wrapContentHeight(),
            onClick = onPlaceButtonClicked
        )
        {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Sharp.Place, contentDescription = "Select Place")
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.select_place)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Step 3: Save event",
            style = MaterialTheme.typography.labelLarge
        )
        Button(modifier = Modifier
//            .fillMaxWidth()
            .wrapContentHeight(),
            onClick = onFinishButtonClicked
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Sharp.Check, contentDescription = "Save Event")
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.save_event)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventInfoPreview(){
    EventInfoScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onTimeButtonClicked = {},
        onPlaceButtonClicked = {},
        onFinishButtonClicked = {}
    )
}