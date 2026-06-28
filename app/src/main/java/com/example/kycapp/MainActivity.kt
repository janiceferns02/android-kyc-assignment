package com.example.kycapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kycapp.ui.accountdetail.AccountDetailScreen
import com.example.kycapp.ui.accountlistdashboard.AccountDashboardScreen
import com.example.kycapp.ui.accountlistdashboard.DashboardViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "account_dashboard"
                    ) {
                        composable("account_dashboard") {
                            val dashboardViewModel: DashboardViewmodel = hiltViewModel()

                            AccountDashboardScreen(
                                viewModel = dashboardViewModel,
                                onDoKycClick = { userId ->
                                    navController.navigate("user_detail/$userId")
                                }
                            )
                        }

                        composable("user_detail/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()

                            AccountDetailScreen(userId)
                        }
                    }
                }
            }
        }
    }
}