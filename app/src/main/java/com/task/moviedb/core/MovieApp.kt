package com.task.moviedb.core

import com.task.moviedb.cache.Cache
import retrofit2.Retrofit

class MovieApp {
    companion object {
        var cache: Cache? = null
        var retrofit: Retrofit? = null
    }
}