package com.example.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.AdminRepository
import com.example.data.BikeSubmission
import com.example.ui.BikeCashButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember { AdminRepository(context) }
    var submissions by remember { mutableStateOf<List<BikeSubmission>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        submissions = repository.getAllSubmissions()
        isLoading = false
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Admin Dashboard") }) }) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
                Text("Total Submissions: ${submissions.size}", style = MaterialTheme.typography.headlineSmall)
                // Add list of submissions
                submissions.forEach { submission ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { 
                        navController.navigate(com.example.navigation.AdminSubmissionDetail(submission.id))
                    }) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("${submission.brand} ${submission.model} - ${submission.status}")
                        }
                    }
                }
            }
        }
    }
}
