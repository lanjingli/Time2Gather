package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R
import com.example.ece_452_project.data.MenuData

/**
 * Composable for setting user info and preferences
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    modifier: Modifier = Modifier,
    dietaryOptions: List<String> = MenuData.dietaryOptions,
    nameText: String = "",
    emailText: String = "",
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    checkboxStates: List<Boolean> = List(MenuData.dietaryOptions.size) {false},
    onCheckboxChange: (Boolean, Int) -> Unit,
    onSaveButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.user_info),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = nameText,
            onValueChange = onNameChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.name)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = emailText,
            onValueChange = onEmailChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.email_address)) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.preferences),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.dietary),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(16.dp))
        dietaryOptions.forEachIndexed { i, item ->
            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(checked = checkboxStates[i], onCheckedChange = { onCheckboxChange(it, i) })
                Text(text = item)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveButtonClicked
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreferencesPreview(){
    PreferencesScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        onNameChange = {new: String -> Unit},
        onEmailChange = {new: String -> Unit},
        onCheckboxChange = {new: Boolean, index: Int -> Unit},
        onSaveButtonClicked = {}
    )
}
