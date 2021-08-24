package com.example.movie_app_compose.util

import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.model.Detail

open class Mapper {

    fun mapDetailToMyMovie(data : Detail): MyMovie{
        return MyMovie(
            data.release_date,
            true,
            data.adult,
            data.backdrop_path,
            data.genres,
            data.vote_count,
            data.original_language,
            data.original_title,
            data.poster_path,
            data.video,
            data.id,
            data.vote_average,
            data.title,
            data.overview,
            data.popularity
        )
    }
}