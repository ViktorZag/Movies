package com.filmcenter.movies.presentation.gallery

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest


@Composable
fun DemoScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.data.collectAsState()
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier) {
            itemsIndexed(uiState.value) { index, movie ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(92.dp)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = movie.title, modifier = Modifier.weight(1f))
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(2.5f)
                    )
                }
            }
        }
    }
}