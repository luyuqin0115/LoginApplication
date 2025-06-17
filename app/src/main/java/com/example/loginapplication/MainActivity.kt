package com.example.loginapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginapplication.ui.HomeScreen
import com.example.loginapplication.ui.LoginScreen
import com.example.loginapplication.ui.RegisterScreen
import com.example.loginapplication.ui.theme.LoginApplicationTheme
import com.example.loginapplication.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginApplicationTheme {
                LoginApp()
            }
        }
    }
}

@Composable
fun LoginApp() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel(context = androidx.compose.ui.platform.LocalContext.current)
    
    // 检查登录状态决定初始页面
    val startDestination = if (authViewModel.isLoggedIn.value) "home" else "login"
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }
        
        composable("register") {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }
        
        composable("home") {
            HomeScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }
    }
}