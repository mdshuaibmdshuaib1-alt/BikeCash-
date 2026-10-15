package com.example.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.AdminRepository
import com.example.navigation.AdminDashboard
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.platform.LocalContext

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AdminLoginScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { AdminRepository(context) }
    var isChecking by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text("Admin Access", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            isChecking = true
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                scope.launch {
                    if (repository.checkIsAdmin(currentUser.uid)) {
                        navController.navigate(AdminDashboard)
                    } else {
                        isChecking = false
                    }
                }
            }
        }) {
            Text("Login as Admin")
        }
    }
}
