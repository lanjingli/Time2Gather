package com.example.ece_452_project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R

@Composable
fun EventInfoScreen(
    modifier: Modifier = Modifier,
    onBackToFriendsClicked: () ->Unit,
    onTimeButtonClicked: () -> Unit,
    onPlaceButtonClicked: () -> Unit,
    onFinishButtonClicked: () -> Unit
){
    // Button: go back to select friends button. Navigate to Select Friends view
    TextButton(
        onClick = onBackToFriendsClicked,
        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier.size(42.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = ""
            )
            Text(
                text = stringResource(R.string.back_to_invite_friends),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(104.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            
            // Title: "Event Details"
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.event_details),
                style = MaterialTheme.typography.headlineSmall
            )
            
            // Button: to select event time. Navigates to Time selection view
            OutlinedButton(
                onClick = onTimeButtonClicked,
                border = BorderStroke(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp).weight(1f),
                        imageVector = Icons.Default.DateRange,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(width = 5.dp))
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(R.string.select_time),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }

            // Button: to select event place. Navigates to place selection view
            OutlinedButton(
                onClick = onPlaceButtonClicked,
                border = BorderStroke(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp).weight(1f),
                        imageVector = Icons.Default.Place,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(width = 5.dp))
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(R.string.select_place),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Button: Complete selections. Saves eventSelected to ui state. Navigate back to dashboard view
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onFinishButtonClicked
        ) {
            Text(
                text = stringResource(R.string.finish),
                fontSize = 16.sp
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
        onBackToFriendsClicked = {},
        onTimeButtonClicked = {},
        onPlaceButtonClicked = {},
        onFinishButtonClicked = {}
    )
}