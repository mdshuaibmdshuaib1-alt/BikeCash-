package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation.BikePhotos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeDetailsScreen(navController: NavController, viewModel: BikeCashViewModel) {
    val state by viewModel.uiState.collectAsState()
    var brand by remember { mutableStateOf(state.brand) }
    var model by remember { mutableStateOf(state.model) }
    var year by remember { mutableStateOf(state.registrationYear) }
    var kilometers by remember { mutableStateOf(state.kilometers) }

    Scaffold(topBar = { TopAppBar(title = { Text("Bike Details") }) }, containerColor = MaterialTheme.colorScheme.background) { innerPaddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPaddingValues).padding(16.dp).verticalScroll(rememberScrollState())) {
            LinearProgressIndicator(progress = { 1f / 3f }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Basic Information", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            
            BikeCashOutlinedTextField(value = brand, onValueChange = { brand = it }, label = "Brand *")
            Spacer(modifier = Modifier.height(8.dp))
            BikeCashOutlinedTextField(value = model, onValueChange = { model = it }, label = "Model *")
            Spacer(modifier = Modifier.height(8.dp))
            BikeCashOutlinedTextField(value = year, onValueChange = { year = it }, label = "Registration Year *")
            Spacer(modifier = Modifier.height(8.dp))
            BikeCashOutlinedTextField(value = kilometers, onValueChange = { kilometers = it }, label = "Kilometres Driven *")
            
            Spacer(modifier = Modifier.height(32.dp))
            BikeCashButton(
                onClick = { 
                    viewModel.updateBikeDetails(brand, model, year, "")
                    viewModel.updateKilometersAndCondition(kilometers, "") // Assuming this needs adding
                    navController.navigate(BikePhotos) 
                },
                text = "Next"
            )
        }
    }
}
