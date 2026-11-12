package com.example.modoguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.modoguardian.data.local.AppDatabase
import com.example.modoguardian.data.preferences.PreferencesManager
import com.example.modoguardian.model.Rol
import com.example.modoguardian.navigation.NavigationEvent
import com.example.modoguardian.navigation.Screen
import com.example.modoguardian.navigation.homeRouteFor
import com.example.modoguardian.ui.components.GuardianBottomBar
import com.example.modoguardian.ui.components.GuardianRoutes
import com.example.modoguardian.ui.screens.AlertaCriticaScreen
import com.example.modoguardian.ui.screens.CamarasScreen
import com.example.modoguardian.ui.screens.EventosScreen
import com.example.modoguardian.ui.screens.HomeAdminScreen
import com.example.modoguardian.ui.screens.HomeOperadorScreen
import com.example.modoguardian.ui.screens.HomeSupervisorScreen
import com.example.modoguardian.ui.screens.ListadoOfflineScreen
import com.example.modoguardian.ui.screens.LoginScreen
import com.example.modoguardian.ui.screens.LoginSuccessScreen
import com.example.modoguardian.ui.screens.PerfilScreen
import com.example.modoguardian.ui.screens.RegistroIntegralScreen
import com.example.modoguardian.ui.screens.ResumenScreen
import com.example.modoguardian.ui.screens.SuccessScreen
import com.example.modoguardian.ui.theme.ModoGuardianTheme
import com.example.modoguardian.viewmodel.LoginViewModel
import com.example.modoguardian.viewmodel.MainViewModel
import com.example.modoguardian.viewmodel.RegistroViewModel
import com.example.modoguardian.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val preferencesManager = PreferencesManager(this)

        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                require(modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
                    "ViewModel desconocido: ${modelClass.name}"
                }
                return UsuarioViewModel(database.usuarioDao(), preferencesManager) as T
            }
        }

        setContent {
            var darkTheme by remember { mutableStateOf(true) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = Unit) {
                preferencesManager.modoOscuroFlow.collect { activo ->
                    darkTheme = activo
                }
            }

            ModoGuardianTheme(darkTheme = darkTheme) {
                val mainViewModel: MainViewModel = viewModel()
                val loginViewModel: LoginViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel(factory = factory)
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val loginState by loginViewModel.estado.collectAsState()
                val rolActual = loginState.rol ?: Rol.OPERADOR
                val incidentMessage = stringResource(R.string.dashboard_incident_sent)

                LaunchedEffect(key1 = Unit) {
                    mainViewModel.navigationEvents.collectLatest { event ->
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

                fun navegar(route: String) {
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }

                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                val roleHomeRoute = homeRouteFor(rolActual)

                val selectedTab = when (currentRoute) {
                    Screen.HomeAdmin.route,
                    Screen.HomeSupervisor.route,
                    Screen.HomeOperador.route -> GuardianRoutes.INICIO
                    Screen.Camaras.route -> GuardianRoutes.CAMARAS
                    Screen.Eventos.route -> GuardianRoutes.EVENTOS
                    Screen.Perfil.route -> GuardianRoutes.PERFIL
                    else -> ""
                }

                val mostrarBottomBar = selectedTab.isNotEmpty()
                val mostrarFab = currentRoute in setOf(
                    Screen.HomeAdmin.route,
                    Screen.HomeSupervisor.route,
                    Screen.HomeOperador.route
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = {
                        if (mostrarBottomBar) {
                            GuardianBottomBar(
                                selectedRoute = selectedTab,
                                onNavigate = { destino ->
                                    when (destino) {
                                        GuardianRoutes.INICIO -> navegar(roleHomeRoute)
                                        GuardianRoutes.CAMARAS -> navegar(Screen.Camaras.route)
                                        GuardianRoutes.EVENTOS -> navegar(Screen.Eventos.route)
                                        GuardianRoutes.ALERTAS -> navegar(Screen.AlertaCritica.route)
                                        GuardianRoutes.PERFIL -> navegar(Screen.Perfil.route)
                                    }
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        if (mostrarFab) {
                            ExtendedFloatingActionButton(
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(incidentMessage)
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Warning,
                                        contentDescription = null
                                    )
                                },
                                text = {
                                    Text(
                                        text = stringResource(R.string.dashboard_report_incident),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                },
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                                shape = RoundedCornerShape(50)
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController = navController, viewModel = loginViewModel)
                        }
                        composable(route = Screen.RegistroIntegralScreen.route) {
                            RegistroIntegralScreen(navController = navController, viewModel = usuarioViewModel)
                        }
                        composable(route = Screen.ResumenScreen.route) {
                            ResumenScreen(
                                viewModel = usuarioViewModel,
                                onVerListado = { navegar(Screen.ListadoOfflineScreen.route) }
                            )
                        }
                        composable(route = Screen.ListadoOfflineScreen.route) {
                            ListadoOfflineScreen(viewModel = usuarioViewModel)
                        }
                        composable(route = Screen.SuccessScreen.route) {
                            SuccessScreen(
                                navController = navController,
                                viewModel = viewModel<RegistroViewModel>()
                            )
                        }
                        composable(route = Screen.LoginSuccessScreen.route) {
                            LoginSuccessScreen(
                                navController = navController,
                                loginViewModel = loginViewModel
                            )
                        }
                        composable(route = Screen.HomeAdmin.route) {
                            HomeAdminScreen(navController = navController, loginViewModel = loginViewModel)
                        }
                        composable(route = Screen.HomeSupervisor.route) {
                            HomeSupervisorScreen(navController = navController, loginViewModel = loginViewModel)
                        }
                        composable(route = Screen.HomeOperador.route) {
                            HomeOperadorScreen(navController = navController, loginViewModel = loginViewModel)
                        }
                        composable(route = Screen.Camaras.route) {
                            CamarasScreen(navController = navController)
                        }
                        composable(route = Screen.Eventos.route) {
                            EventosScreen(navController = navController)
                        }
                        composable(route = Screen.AlertaCritica.route) {
                            AlertaCriticaScreen(navController = navController)
                        }
                        composable(route = Screen.Perfil.route) {
                            PerfilScreen(
                                isDarkTheme = darkTheme,
                                onToggleDarkTheme = { activo ->
                                    darkTheme = activo
                                    scope.launch {
                                        preferencesManager.guardarModoOscuro(activo)
                                    }
                                },
                                onCerrarSesion = {
                                    loginViewModel.cerrarSesion()
                                    navController.navigate(Screen.LoginScreen.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onVerCamaras = { navegar(Screen.Camaras.route) },
                                onVerEventos = { navegar(Screen.Eventos.route) }
                            )
                        }
                    }
                }
            }
        }
    }
}
