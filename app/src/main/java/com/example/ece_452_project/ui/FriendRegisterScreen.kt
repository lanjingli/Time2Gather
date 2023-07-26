package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R

/**
 * Composable for adding a new friend
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendRegisterScreen(
    modifier: Modifier = Modifier,
    usernameText: String = "",
    onUsernameChange: (String) -> Unit,
    onSignupButtonClicked: () -> Unit,
    onBackToEventInfoClicked: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                    onClick = onBackToEventInfoClicked,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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
                            text = stringResource(R.string.back_to_friends_page),
                            style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                        text = stringResource(R.string.add_new_friend),
                        style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = stringResource(R.string.enter_their_username),
                        style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                        value = usernameText,
                        onValueChange = onUsernameChange,
                        singleLine = true,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
                        label = { Text(stringResource(R.string.username)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                        onClick = onSignupButtonClicked
                ) {
                    Text(
                            text = stringResource(R.string.add_new_friend),
                            fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendRegisterPreview(){
    FriendRegisterScreen(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onUsernameChange = {new: String -> Unit },
        onSignupButtonClicked = { },
        onBackToEventInfoClicked = { }
    )
}

