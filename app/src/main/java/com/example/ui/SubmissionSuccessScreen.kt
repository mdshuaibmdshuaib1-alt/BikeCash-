package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation.Home

@Composable
fun SubmissionSuccessScreen(navController: NavController, submissionId: String) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Submission Received", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Submission ID: $submissionId", style = MaterialTheme.typography.bodyLarge)
            Text("Status: Pending Review", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(32.dp))
            BikeCashButton(
                onClick = {
                    val popped = navController.popBackStack(Home, false)
                    if (!popped) {
                        navController.navigate(Home)
                    }
                },
                text = "Back to Home"
            )
        }
    }
}
