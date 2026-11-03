package com.example.modoguardian.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.modoguardian.ui.utils.obtainWindowSizeClass
import com.example.modoguardian.viewmodel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val windowSizeClass = obtainWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> LoginCompact(navController = navController, viewModel = viewModel)
        else -> LoginMedium(navController = navController, viewModel = viewModel)
    }
}
