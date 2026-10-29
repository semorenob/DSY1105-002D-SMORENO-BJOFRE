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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modoguardian.data.local.AppDatabase
import com.example.modoguardian.data.preferences.PreferencesManager
import com.example.modoguardian.navigation.NavigationEvent
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.ui.screens.ListadoOfflineScreen
import com.example.modoguardian.ui.screens.LoginScreen
import com.example.modoguardian.ui.screens.PerfilScreen
import com.example.modoguardian.ui.screens.RegistroIntegralScreen
import com.example.modoguardian.ui.screens.RegistroScreen
import com.example.modoguardian.ui.screens.ResumenScreen
import com.example.modoguardian.ui.screens.StartPage
import com.example.modoguardian.ui.screens.SuccessScreen
import com.example.modoguardian.ui.theme.ModoGuardianTheme
import com.example.modoguardian.viewmodel.LoginViewModel
import com.example.modoguardian.viewmodel.MainViewModel
import com.example.modoguardian.viewmodel.PerfilViewModel
import com.example.modoguardian.viewmodel.RegistroViewModel
import com.example.modoguardian.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val preferencesManager = PreferencesManager(this)

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UsuarioViewModel(database.usuarioDao(), preferencesManager) as T
            }
        }

        val viewModel = ViewModelProvider(this, factory)[UsuarioViewModel::class.java]

        setContent {
            ModoGuardianTheme {
                val viewModel: MainViewModel = viewModel()
                val loginViewModel: LoginViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel()
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
                        composable(route = Screen.RegistroIntegralScreen.route) {
                            RegistroIntegralScreen(navController = navController, viewModel = usuarioViewModel)
                        }
                        composable(route = Screen.ResumenScreen.route) {
                            ResumenScreen(viewModel = usuarioViewModel, onVerListado = {navController.navigate(Screen.ListadoOfflineScreen.route)})
                        }
                        composable(route = Screen.ListadoOfflineScreen.route) {
                            ListadoOfflineScreen(viewModel = usuarioViewModel)
                        }
                    }
                }
            }
        }
    }
}