package com.filmcenter.movies.data.remote

import com.filmcenter.movies.data.remote.api.ApiService
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemoteRepositoryInml @Inject constructor(
    private val apiService: ApiService
) : RemoteRepository {

    override suspend fun getAllMovies() = apiService.getTopRatedMovies()
}