package com.example.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.navigation.SellerContact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikePhotosScreen(navController: NavController, viewModel: BikeCashViewModel) {
    val state by viewModel.uiState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        uris.forEach { viewModel.addPhoto(it) }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Bike Photos") }) }, containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            LinearProgressIndicator(progress = { 2f / 3f }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Add clear photos of your bike", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Text("Select Photos")
            }
            
            LazyRow(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                items(state.photoUris) { uri ->
                    AsyncImage(model = uri, contentDescription = "Bike Photo", modifier = Modifier.size(150.dp).padding(4.dp))
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            Row {
                BikeCashButton(onClick = { navController.popBackStack() }, text = "Back", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                BikeCashButton(onClick = { navController.navigate(SellerContact) }, text = "Next", modifier = Modifier.weight(1f))
            }
        }
    }
}
