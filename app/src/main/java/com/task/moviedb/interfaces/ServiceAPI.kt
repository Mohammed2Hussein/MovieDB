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
    , @Query("lang") lang: String
    , @Query("query") query: String
    , @Query("page") page: Int
    , @Query("include_adult") include_adult: Int
    , @Query("region") region: Int
    , @Query("year") year: Int
    , @Query("primary_release_year") primary_release_year: Int): Observable<MovieSearchResponse>
}