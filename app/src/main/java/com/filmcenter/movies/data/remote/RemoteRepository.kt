package com.filmcenter.movies.data.remote

import com.filmcenter.movies.data.remote.api.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getAllMovies(): MovieResponse
}