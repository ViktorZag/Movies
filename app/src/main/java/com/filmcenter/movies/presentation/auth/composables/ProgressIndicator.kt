package com.filmcenter.movies.presentation.auth.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    isShown: Boolean
) {
    if (isShown) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .alpha(0.06f)
                .background(MaterialTheme.colorScheme.onSurface)
        )
        CircularProgressIndicator()
    }
}