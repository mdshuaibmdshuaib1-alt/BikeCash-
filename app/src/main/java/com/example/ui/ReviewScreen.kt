package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.PriceEstimate
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavController, viewModel: BikeCashViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Review Details") }) },
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Bike Information", style = MaterialTheme.typography.titleMedium)
                    Text("Brand: ${state.brand}")
                    Text("Model: ${state.model}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Contact Information", style = MaterialTheme.typography.titleMedium)
                    Text("Name: ${state.fullName}")
                    Text("Mobile: ${state.mobileNumber}")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            val submission = com.example.data.BikeSubmission(
                brand = state.brand,
                model = state.model,
                registrationYear = state.registrationYear,
                registrationState = state.registrationState,
                kilometers = state.kilometers.toLongOrNull() ?: 0L,
                condition = state.condition,
                sellerName = state.fullName,
                sellerMobile = state.mobileNumber
            )
            val estimate = viewModel.getEstimate(submission)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Market Estimation", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(estimate.message, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            val context = androidx.compose.ui.platform.LocalContext.current
            val repository = remember { com.example.data.BikeRepository(context) }
            var isSubmitting by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            var currentUser by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

            if (currentUser == null) {
                GoogleSignInButton(
                    onAuthSuccess = { currentUser = FirebaseAuth.getInstance().currentUser },
                    onAuthError = { /* Handle error */ }
                )
            } else {
                BikeCashButton(
                    onClick = {
                        if (state.brand.isBlank() || state.model.isBlank() || state.fullName.isBlank() || state.mobileNumber.isBlank()) {
                            scope.launch { /* Show snackbar */ }
                            return@BikeCashButton
                        }
                        isSubmitting = true
                        scope.launch {
                            try {
                                val photoUrls = state.photoUris.map { uri -> repository.uploadPhoto(uri) }
                                val finalSubmission = submission.copy(estimate = estimate, photos = photoUrls)
                                viewModel.submitSubmission(finalSubmission, repository) { id, error ->
                                    if (id != null) {
                                        navController.navigate(com.example.navigation.SubmissionSuccess(id)) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    } else {
                                        isSubmitting = false
                                        // Show error
                                    }
                                }
                            } catch (e: Exception) {
                                isSubmitting = false
                                // Show error
                            }
                        }
                    },
                    text = if (isSubmitting) "Submitting..." else "Submit",
                    enabled = !isSubmitting
                )
            }
        }
    }
}
