package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation.BikeDetails
import com.example.navigation.HowItWorks

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text("BikeCash", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
            Text("Sell Your Bike. Get Paid.", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Sell Your Bike, The Easy Way.", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            
            BikeCashButton(
                onClick = { navController.navigate(BikeDetails) },
                text = "Sell My Bike"
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            BikeCashButton(
                onClick = { navController.navigate(HowItWorks) },
                text = "How It Works"
            )
        }
    }
}
