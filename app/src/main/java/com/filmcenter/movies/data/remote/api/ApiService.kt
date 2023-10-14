package com.filmcenter.movies.data.remote.api

import com.filmcenter.movies.data.remote.api.model.MovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("movie/upcoming?language=en-US")
    suspend fun getUpcomingMovies(): MovieResponse

    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(): MovieResponse
}