package com.filmcenter.movies.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filmcenter.movies.data.remote.RemoteRepository
import com.filmcenter.movies.data.remote.api.model.MovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: RemoteRepository) :
    ViewModel() {

    val data = MutableStateFlow(listOf<MovieDto>())

    init {
        getAllMovies()
    }
    fun getAllMovies() {
        viewModelScope.launch {
            val newData = remoteRepository.getAllMovies().movieList
            data.update {
                newData
            }
        }
    }
}