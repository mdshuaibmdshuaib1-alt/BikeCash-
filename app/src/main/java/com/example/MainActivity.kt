package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.navigation.compose.rememberNavController
import com.example.navigation.Home
import com.example.navigation.BikeDetails
import com.example.ui.HomeScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        BikeCashApp()
      }
    }
  }
}

@Composable
fun BikeCashApp() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        val viewModel: com.example.ui.BikeCashViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<Home> { HomeScreen(navController) }
            composable<BikeDetails> { com.example.ui.BikeDetailsScreen(navController, viewModel) }
            composable<com.example.navigation.BikePhotos> { com.example.ui.BikePhotosScreen(navController, viewModel) }
            composable<com.example.navigation.SellerContact> { com.example.ui.SellerContactScreen(navController, viewModel) }
            composable<com.example.navigation.Review> { com.example.ui.ReviewScreen(navController, viewModel) }
            composable<com.example.navigation.HowItWorks> { com.example.ui.HowItWorksScreen(navController) }
            composable<com.example.navigation.AdminLogin> { com.example.ui.admin.AdminLoginScreen(navController) }
            composable<com.example.navigation.AdminDashboard> { com.example.ui.admin.AdminDashboardScreen(navController) }
            composable<com.example.navigation.AdminSubmissionDetail> { backStackEntry ->
                val args = backStackEntry.toRoute<com.example.navigation.AdminSubmissionDetail>()
                com.example.ui.admin.AdminSubmissionDetailScreen(navController, args.submissionId)
            }
            composable<com.example.navigation.SubmissionSuccess> { backStackEntry ->
                val args = backStackEntry.toRoute<com.example.navigation.SubmissionSuccess>()
                com.example.ui.SubmissionSuccessScreen(navController, args.submissionId)
            }
        }
    }
}
