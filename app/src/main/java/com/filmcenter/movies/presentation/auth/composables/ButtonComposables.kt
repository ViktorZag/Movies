package com.filmcenter.movies.presentation.auth.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.filmcenter.movies.presentation.theme.MoviesTheme

@Composable
fun NormalButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .height(MoviesTheme.dimens.normalButtonSize)
            .requiredWidth(MoviesTheme.dimens.minButtonWidth),
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onTertiary),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    isLoading: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        ),
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}