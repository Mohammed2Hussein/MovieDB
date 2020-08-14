package com.task.moviedb.interfaces

import com.task.moviedb.model.MovieDiscoverResponse
import com.task.moviedb.model.MovieSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {
    @GET("discover/movie")
    fun discoverMovies(@Query("api_key") api_key: String, @Query("page") page: Int): Observable<MovieDiscoverResponse>

    @GET("search/movie")
    fun searchMovies(@Query("api_key") api_key: String
    , @Query("page") page: Int
    , @Query("lang") lang: String
    , @Query("query") query: String
    , @Query("include_adult") include_adult: Boolean
    , @Query("year") year: Int): Observable<MovieSearchResponse>
}