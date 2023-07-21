package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.ece_452_project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendMainScreen() {
    Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.createScreenTEMP),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}