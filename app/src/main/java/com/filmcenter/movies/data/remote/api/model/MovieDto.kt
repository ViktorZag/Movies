package com.filmcenter.movies.data.remote.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String,
    @Json(name ="overview")
    val description: String,
    @Json(name ="release_date")
    val releaseDate: String,
    @Json(name ="vote_average")
    val rating: String,
    val backdrop_path: String,
    val poster_path: String,
    @Json(name ="genre_ids")
    val genreIds: List<Int>
)

