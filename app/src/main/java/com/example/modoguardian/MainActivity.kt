package com.example.modoguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modoguardian.navigation.NavigationEvent
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.screens.LoginScreen
import com.example.modoguardian.ui.screens.PerfilScreen
import com.example.modoguardian.ui.screens.RegistroScreen
import com.example.modoguardian.ui.screens.StartPage
import com.example.modoguardian.ui.screens.SuccessScreen
import com.example.modoguardian.ui.theme.ModoGuardianTheme
import com.example.modoguardian.viewmodel.LoginViewModel
import com.example.modoguardian.viewmodel.MainViewModel
import com.example.modoguardian.viewmodel.PerfilViewModel
import com.example.modoguardian.viewmodel.RegistroViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModoGuardianTheme {
                val viewModel: MainViewModel = viewModel()
                val loginViewModel: LoginViewModel = viewModel()
                val registroViewModel: RegistroViewModel = viewModel()
                val perfilViewModel: PerfilViewModel = viewModel()
                val navController = rememberNavController()

                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popUptoRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }

                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.StartPage.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.StartPage.route) {
                            StartPage(navController = navController)
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController = navController, viewModel = loginViewModel)
                        }
                        composable(route = Screen.RegistroScreen.route) {
                            RegistroScreen(navController = navController, viewModel = registroViewModel)
                        }
                        composable(route = Screen.SuccessScreen.route) {
                            SuccessScreen(navController = navController, viewModel = registroViewModel)
                        }
                        composable(route = Screen.PerfilScreen.route) {
                            PerfilScreen(viewModel = perfilViewModel)
                        }
                    }
                }
            }
        }
    }
}