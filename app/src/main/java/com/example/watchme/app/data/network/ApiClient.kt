package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.MovieCreditsResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.data.network.responses.ReviewsResponse
import com.example.watchme.app.data.network.responses.SeasonDetails
import com.example.watchme.app.data.network.responses.SeriesDetailsResponse
import com.example.watchme.app.data.network.responses.SeriesResponse
import com.example.watchme.app.data.network.responses.VideoResponse
import com.example.watchme.core.constants.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiClient {

    // MOVIES

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
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("watch/providers/movie${Constants.SPANISH_AR}${Constants.AR_REGION}")
    suspend fun getProviders(): Response<ProvidersResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getMovieDetailsById(@Url url:String): Response<DetailsMovieResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getMovieCreditsById(@Url url:String): Response<MovieCreditsResponse>



    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getRecommendationsById(@Url url:String): Response<MovieResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getReviewsById(@Url url:String): Response<ReviewsResponse>



    // SERIES

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("tv/popular")
    suspend fun getPopularSeries(): Response<SeriesResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("tv/airing_today")
    suspend fun getAiringTodaySeries(): Response<SeriesResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("tv/on_the_air")
    suspend fun getOnTheAirSeries(): Response<SeriesResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(): Response<SeriesResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET()
    suspend fun getSeriesDetailsById(@Url url:String): Response<SeriesDetailsResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET()
    suspend fun getSeasonsDetailsById(@Url url:String): Response<SeasonDetails>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET()
    suspend fun getSeriesRecommendationsById(@Url url:String): Response<SeriesResponse>

    // BOTH

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getImageListById(@Url url:String): Response<ImageBackdrop>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getVideosById(@Url url:String): Response<VideoResponse>

}