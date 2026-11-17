package com.example.modoguardian.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.modoguardian.ui.theme.GuardianTheme

/**
 * Tarjeta base del sistema de diseno Guardian.
 */
@Composable
fun GuardCard(
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, GuardianTheme.colors.border.copy(alpha = 0.7f))
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}

/**
 * Contenedor cuadrado para iconos con color semantico.
 */
@Composable
fun SemanticIconBox(
    icon: ImageVector,
    tint: Color,
    containerColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    iconSize: Dp = 20.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * Etiqueta compacta para estados (verificado, pendiente, critico, etc.).
 */
@Composable
fun StatusBadge(
    text: String,
    color: Color,
    containerColor: Color,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}

/**
 * Boton principal de ancho completo.
 */
@Composable
fun GuardPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !loading,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = Color.White
            )
            Spacer(Modifier.size(8.dp))
        } else if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.size(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/**
 * Boton secundario con borde configurable.
 */
@Composable
fun GuardOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = contentColor)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(17.dp)
            )
            Spacer(Modifier.size(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/**
 * Campo de texto con etiqueta, icono inicial, icono final y mensaje de error.
 */
@Composable
fun GuardTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            leadingIcon = leadingIcon?.let { vector ->
                {
                    Icon(
                        imageVector = vector,
                        contentDescription = null,
                        modifier = Modifier.size(19.dp)
                    )
                }
            },
            trailingIcon = trailingIcon?.let { vector ->
                {
                    Icon(
                        imageVector = vector,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .let { base ->
                                if (onTrailingIconClick != null) {
                                    base.clickable(onClick = onTrailingIconClick)
                                } else {
                                    base
                                }
                            }
                    )
                }
            },
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp)
        )
        if (isError && errorText != null) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.bodySmall,
                color = GuardianTheme.colors.critical,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }
    }
}

/**
 * Fila con icono, titulo, descripcion opcional e interruptor.
 */
@Composable
fun GuardToggleRow(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SemanticIconBox(
            icon = icon,
            tint = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            size = 36.dp,
            iconSize = 18.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * Fila de menu con icono, titulo, subtitulo y contenido final opcional.
 */
@Composable
fun ProfileRow(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: () -> Unit = {},
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SemanticIconBox(
            icon = icon,
            tint = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            size = 36.dp,
            iconSize = 18.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (trailing != null) {
            trailing()
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Separador circular pequeno usado en resumenes de estado.
 */
@Composable
fun StatusDot(color: Color, modifier: Modifier = Modifier, size: Dp = 7.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

/**
 * Etiqueta numerica compacta.
 */
@Composable
fun CountPill(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = containerColor
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
