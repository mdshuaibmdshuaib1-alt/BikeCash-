package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerContactScreen(navController: NavController, viewModel: BikeCashViewModel) {
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Seller Contact") }) }, containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            LinearProgressIndicator(progress = { 1f }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Your Contact Information", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            
            BikeCashOutlinedTextField(value = name, onValueChange = { name = it }, label = "Full Name *")
            Spacer(modifier = Modifier.height(8.dp))
            BikeCashOutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = "Mobile Number *")
            
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                BikeCashButton(onClick = { navController.popBackStack() }, text = "Back", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                BikeCashButton(
                    onClick = { 
                        viewModel.updateContactDetails(name, mobile)
                        navController.navigate(Review) 
                    },
                    text = "Next",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
