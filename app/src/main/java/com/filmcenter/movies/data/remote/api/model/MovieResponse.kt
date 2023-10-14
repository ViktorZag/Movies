package com.filmcenter.movies.data.remote.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "results")
    val movieList: List<MovieDto>
)
