package com.example.ece_452_project.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.User
import java.util.Calendar

/**
 * Composable for the initial sign up screen
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventSettingScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    eventNameText: String = "",
    eventDescText: String = "",
    onEventNameChange: (String) -> Unit,
    onEventDescChange: (String) -> Unit,
    onInviteFriendClicked: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        // Page Title: New Event
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.new_event),
            style = MaterialTheme.typography.headlineMedium
        )

        // TextField: user inputted event name
        TextField(
            value = eventNameText,
            onValueChange = onEventNameChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.name)) },
        )
        Spacer(modifier = Modifier.height(16.dp))

        // TextField: user inputted event description
        TextField(
            value = eventDescText,
            onValueChange = onEventDescChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.event_description)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button: invite your friends. Saves state to selectedEvent. Navigate to friend selection view
        Button(
            onClick = onInviteFriendClicked,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(10.dp),
                    imageVector = Icons.Default.Share,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(width = 15.dp))
                Text(
                    text = stringResource(R.string.invite_friends),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventSettingPreview(){
    EventSettingScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onEventNameChange = { new: String -> Unit },
        onEventDescChange = { new: String -> Unit },
        onInviteFriendClicked = {}
        )
}