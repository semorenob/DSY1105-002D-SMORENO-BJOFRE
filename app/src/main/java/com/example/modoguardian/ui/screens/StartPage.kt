package com.example.modoguardian.ui.screens

import com.example.modoguardian.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
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
        // TODO: Cambiar de logo a imagen
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.paomedia_small_n_flat_shield),
            contentDescription = "Logo ModoGuardian",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Modo Guardian", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("registro")
            },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
        ) {
            Text("Registrarse", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(2.dp))

        Button(
            onClick = {
                navController.navigate("login")
            },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
        ) {
            Text("Iniciar Sesión", style = MaterialTheme.typography.labelLarge)
        }


        }
    }