package com.example.modoguardian.ui.components

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modoguardian.R
import com.example.modoguardian.model.LoginUiState
import com.example.modoguardian.ui.theme.GuardianTheme

/**
 * Formulario de inicio de sesion compartido por las variantes compact y medium.
 */
@Composable
fun LoginForm(
    estado: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRecordarmeChange: (Boolean) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmit: () -> Unit,
    onCreateAccount: () -> Unit,
    modifier: Modifier = Modifier,
    onGoogleLogin: () -> Unit
) {

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SemanticIconBox(
                icon = Icons.Outlined.CheckCircle,
                tint = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                size = 36.dp,
                iconSize = 19.dp
            )
            Column {
                Text(
                    text = stringResource(R.string.login_brand),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.login_brand_sub),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(26.dp))

        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        GuardTextField(
            value = estado.email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.login_email_label),
            modifier = Modifier.testTag("login_email"),
            leadingIcon = Icons.Outlined.Email,
            isError = estado.errores.email != null,
            errorText = estado.errores.email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(Modifier.height(14.dp))

        GuardTextField(
            value = estado.password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.login_password_label),
            modifier = Modifier.testTag("login_password"),
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = if (estado.passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
            onTrailingIconClick = onTogglePasswordVisibility,
            isError = estado.errores.password != null,
            errorText = estado.errores.password,
            visualTransformation = if (estado.passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        estado.errores.credenciales?.let { mensaje ->
            Spacer(Modifier.height(10.dp))
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = GuardianTheme.colors.criticalContainer
            ) {
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodySmall,
                    color = GuardianTheme.colors.critical,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.recordarme,
                onCheckedChange = onRecordarmeChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = Color.White,
                    uncheckedColor = GuardianTheme.colors.border
                )
            )
            Text(
                text = stringResource(R.string.login_remember),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = { }) {
                Text(
                    text = stringResource(R.string.login_forgot),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        GuardPrimaryButton(
            text = stringResource(R.string.login_submit),
            onClick = onSubmit,
            loading = estado.isLoading
        )

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = GuardianTheme.colors.border.copy(alpha = 0.6f)
            ) {}
            Text(
                text = stringResource(R.string.login_continue_with),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = GuardianTheme.colors.border.copy(alpha = 0.6f)
            ) {}
        }

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GuardOutlinedButton(
                text = stringResource(R.string.login_google),
                onClick = onGoogleLogin,
                enabled = !estado.isLoading,
                modifier = Modifier.weight(1f)
            )
            GuardOutlinedButton(
                text = stringResource(R.string.login_apple),
                onClick = { },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.login_no_account),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = " " + stringResource(R.string.login_create_account),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(onClick = onCreateAccount)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.login_encryption),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(11.dp)
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = stringResource(R.string.login_permissions_hint),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
