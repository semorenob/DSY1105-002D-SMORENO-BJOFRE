package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartPage(
    navController: NavController,
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Modo Guardian", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {
                navController.navigate("registro")
            },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
        ) {
            Text("Registrar", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate("login")
            },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
        ) {
            Text("Iniciar Sesión", style = MaterialTheme.typography.labelLarge)
        }

        Button(
            onClick = {
                navController.navigate("perfil")
            },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
        ) {
            Text("Perfil test")
        }
        }
    }