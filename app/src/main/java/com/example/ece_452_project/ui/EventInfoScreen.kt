package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        Button(modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
            onClick = onTimeButtonClicked
        ) {
            Text(
                text = stringResource(R.string.select_time)
            )
        }
        Button(modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
            onClick = onPlaceButtonClicked
        ) {
            Text(
                text = stringResource(R.string.select_place)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
            onClick = onFinishButtonClicked
        ) {
            Text(
                text = stringResource(R.string.finish)
            )
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