package com.example.modoguardian.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.modoguardian.R

@Composable
fun IntelligentImage(
    imageUri: Uri?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Imagen de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.person_24px),
                contentDescription = "Perfil por defecto",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}