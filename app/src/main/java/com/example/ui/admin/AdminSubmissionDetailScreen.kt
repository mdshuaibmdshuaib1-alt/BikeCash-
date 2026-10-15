package com.example.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.AdminRepository
import com.example.data.BikeSubmission
import com.example.data.Expense
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSubmissionDetailScreen(navController: NavController, submissionId: String) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember { AdminRepository(context) }
    var submission by remember { mutableStateOf<BikeSubmission?>(null) }
    var expenses by remember { mutableStateOf<List<Expense>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(submissionId) {
        // In a real implementation, you would have a repository method to getSubmissionById(submissionId)
        // For this demonstration, we focus on the expense tracking workflow.
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Submission Detail") }) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text("Workflow Status: ${submission?.status ?: "Pending Review"}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Expenses", style = MaterialTheme.typography.titleLarge)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(expenses) { expense ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text("${expense.category}: ₹${expense.amount}", modifier = Modifier.padding(16.dp))
                    }
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        repository.addExpense(submissionId, Expense(amount = 500.0, category = "Cleaning", description = "Deep wash"))
                        expenses = repository.getExpenses(submissionId)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Cleaning Expense")
            }
        }
    }
}
