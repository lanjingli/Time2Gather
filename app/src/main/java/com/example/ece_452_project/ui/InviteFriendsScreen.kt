package com.example.ece_452_project.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.Event
import com.example.ece_452_project.data.MenuData
import com.example.ece_452_project.data.User
import com.example.ece_452_project.ui.components.Day
import com.example.ece_452_project.ui.components.DaysOfWeekTitle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.Calendar


/**
 * Composable for the initial sign up screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteFriendsScreen(
    modifier: Modifier = Modifier,
    user: User = User(),
    onInviteClick: () -> Unit,
    checkFriendStates: MutableList<Boolean> = mutableListOf<Boolean>(),
//    onCheckFriendChange: (User, Boolean, Int) -> Unit
    onCheckFriendChange: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.invite_friends),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
//        user.friends.forEachIndexed { i, friend ->
//            Row(verticalAlignment = Alignment.CenterVertically){
//                Checkbox(checked = checkFriendStates[i], onCheckedChange = { onCheckFriendChange(user, it, i) })
//                Text(
//                    modifier = Modifier.padding(start = 2.dp),
//                    text = friend,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        }
        user.friends.forEach {friend ->
            var checked by remember {
                mutableStateOf(false)
            }
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked_ ->
                        checked = checked_
                    }
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = friend,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onInviteClick
        ) {
            Text(
                text = stringResource(R.string.invite),
                fontSize = 16.sp
            )
        }
    }




}

@Preview(showBackground = true)
@Composable
fun InviteFriendsPreview() {
    InviteFriendsScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        //onCheckFriendChange = {user: User, new: Boolean, index: Int -> Unit},
        onCheckFriendChange = {},
        onInviteClick = {}
    )
}

