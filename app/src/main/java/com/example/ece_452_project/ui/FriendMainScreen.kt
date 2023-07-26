package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.DummyData
import com.example.ece_452_project.data.User
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendMainScreen(
    user : User,
    friends : List<User>,
    onAddNewFriendClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(
                    text = stringResource(R.string.friends),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                onClick = onAddNewFriendClicked
            ) {
                Text(
                    text = stringResource(R.string.add_new_friend),
                    color = Color.White
                )
            }
        }
        for (friend in friends) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = friend.name,
                    fontSize = 18.sp
                )
                Text(
                    text = if (isAvailable(friend)) "Available" else "Not Available",
                    color = if (isAvailable(friend)) Color(0XFF006400) else Color(0XFF800000),
                    fontSize = 18.sp
                )
            }
        }
    }
}

fun isAvailable(
        user: User,
        time: LocalDateTime = LocalDateTime.now()
) : Boolean {
    user.schedule.forEach { event ->
        if ((event.start < time && event.end > time))
            return false
    }
    return true
}

@Preview(showBackground = true)
@Composable
fun FriendMainScreenPreview(){
    FriendMainScreen(
            user = DummyData.users.first { it.username == "alpha" },
            friends = DummyData.users.filter {it.username != "alpha"},
            onAddNewFriendClicked = {}
    )
}