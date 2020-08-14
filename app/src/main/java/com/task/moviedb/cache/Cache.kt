package com.task.moviedb.cache

import com.task.moviedb.model.MovieDiscoverResponse
import com.task.moviedb.model.MovieSearchResponse

class Cache {
    val numberList: MutableList<String> = ArrayList()

    var movieDiscoverResponse: MovieDiscoverResponse? = null
    var movieSearchResponse: MovieSearchResponse? = null
}