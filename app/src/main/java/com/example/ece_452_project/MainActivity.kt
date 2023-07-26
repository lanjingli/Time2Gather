package com.example.ece_452_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ece_452_project.remote.FirestoreUtils
import com.example.ece_452_project.ui.theme.ECE_452_ProjectTheme
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // uncomment these statements to create/reset db state
        FirestoreUtils.testDisc()
        FirestoreUtils.resetDummyUsers()
        FirestoreUtils.resetDummyEvents()
        super.onCreate(savedInstanceState)
        setContent {
            ECE_452_ProjectTheme {
                InfoNavGraph()
            }
        }
    }
}

