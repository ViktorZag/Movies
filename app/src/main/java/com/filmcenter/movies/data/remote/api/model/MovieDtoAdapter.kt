package com.filmcenter.movies.data.remote.api.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
class MovieDtoAdapter {
    @FromJson
    fun fromJson(movieDto: MovieDto): MovieDto {
        return movieDto.copy(
            backdrop_path = "$BASE_IMAGE_URL${movieDto.backdrop_path}",
            poster_path = "$BASE_IMAGE_URL${movieDto.poster_path}"
        )
    }

    @ToJson
    fun toJson(movieDto: MovieDto):MovieDto {
        return movieDto.copy(
            backdrop_path = movieDto.backdrop_path.substringAfter(BASE_IMAGE_URL),
            poster_path = movieDto.poster_path.substringAfter(BASE_IMAGE_URL)
        )
    }
}