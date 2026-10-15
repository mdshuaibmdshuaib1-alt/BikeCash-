package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowItWorksScreen(navController: NavController) {
    Scaffold(topBar = { TopAppBar(title = { Text("How It Works") }) }, containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text("Simple. Safe. Transparent.", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("1. Submit Bike Details\n2. BikeCash Contacts You\n3. Inspection\n4. Final Offer\n5. Purchase & Payment", style = MaterialTheme.typography.bodyLarge)
            
            Spacer(modifier = Modifier.weight(1f))
            BikeCashButton(onClick = { navController.popBackStack() }, text = "Back")
        }
    }
}
