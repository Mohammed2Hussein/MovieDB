package com.task.moviedb.cache

import com.task.moviedb.model.MovieDiscoverResponse

class Cache {
    val numberList: MutableList<String> = ArrayList()

    var movieDiscoverResponse: MovieDiscoverResponse? = null
}