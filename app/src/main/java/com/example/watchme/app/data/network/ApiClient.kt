package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.core.Routes
import com.example.watchme.core.constants.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

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

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("watch/providers/movie")
    suspend fun getProviders(): Response<ProvidersResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getMovieDetailsById(@Url url:String): Response<DetailsMovieResponse>

}