package com.example.ece_452_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ece_452_project.R

/**
 * Composable for the initial sign up screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    emailText: String = "",
    passwordText: String = "",
    cpasswordText: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCPasswordChange: (String) -> Unit,
    onSignupButtonClicked: () -> Unit,
    openDialog: Boolean = false,
    onDialogDismiss: () -> Unit,
    dialogText: String = ""
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (openDialog){
            AlertDialog(
                onDismissRequest = onDialogDismiss,
                confirmButton = { Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDialogDismiss
                ) {
                    Text("Dismiss")
                }
                },
                text = {Text(dialogText, textAlign = TextAlign.Center)}
            )
        }
        Text(
            text = stringResource(R.string.register),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.create_your_account),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = emailText,
            onValueChange = onEmailChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.email_address)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = passwordText,
            onValueChange = onPasswordChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.password)) },
            visualTransformation =  PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = cpasswordText,
            onValueChange = onCPasswordChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            label = { Text(stringResource(R.string.confirm_password)) },
            visualTransformation =  PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSignupButtonClicked
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    RegisterScreen(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        onEmailChange = { new: String -> Unit },
        onPasswordChange = {new: String -> Unit },
        onCPasswordChange = {new: String -> Unit },
        onSignupButtonClicked = { },
        onDialogDismiss = { },
        dialogText = "test"
    )
}

