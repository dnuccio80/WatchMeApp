package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.core.constants.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiClient {

    @Headers(Constants.ACCEPT_JSON, Constants.AUTHORIZATION_TOKEN_API)
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

}