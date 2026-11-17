package com.example.modoguardian.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.modoguardian.R

/**
 * Rutas logicas usadas por la barra de navegacion inferior.
 */
object GuardianRoutes {
    const val INICIO = "guardian_inicio"
    const val CAMARAS = "guardian_camaras"
    const val EVENTOS = "guardian_eventos"
    const val ALERTAS = "guardian_alertas"
    const val PERFIL = "guardian_perfil"
}

private data class GuardianNavItem(
    val route: String,
    val labelRes: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun GuardianBottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        GuardianNavItem(GuardianRoutes.INICIO, R.string.nav_inicio, Icons.Outlined.Home),
        GuardianNavItem(GuardianRoutes.CAMARAS, R.string.nav_camaras, Icons.Outlined.Videocam),
        GuardianNavItem(GuardianRoutes.EVENTOS, R.string.nav_eventos, Icons.Outlined.History),
        GuardianNavItem(GuardianRoutes.ALERTAS, R.string.nav_alertas, Icons.Outlined.Warning),
        GuardianNavItem(GuardianRoutes.PERFIL, R.string.nav_perfil, Icons.Outlined.Person)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val selected = selectedRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.labelRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.labelRes),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
