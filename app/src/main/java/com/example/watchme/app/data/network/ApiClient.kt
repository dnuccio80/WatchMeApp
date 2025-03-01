package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.CollectionResponse
import com.example.watchme.app.data.network.responses.CollectionSearchResponse
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.CreditsResponse
import com.example.watchme.app.data.network.responses.EpisodeResponse
import com.example.watchme.app.data.network.responses.ImagePeopleResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.PeopleDetailsResponse
import com.example.watchme.app.data.network.responses.PeopleMovieInterpretationResponse
import com.example.watchme.app.data.network.responses.PeopleSeriesInterpretationResponse
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
    suspend fun getRecommendationsById(@Url url:String): Response<MovieResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getReviewsById(@Url url:String): Response<ReviewsResponse>

    // COLLECTIONS

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getCollectionDetailsById(@Url url:String): Response<CollectionResponse>

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

    // EPISODES

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET()
    suspend fun getEpisodesDetailsById(@Url url:String): Response<EpisodeResponse>

    // SERIES & MOVIES

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getImageListById(@Url url:String): Response<ImageBackdrop>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getVideosById(@Url url:String): Response<VideoResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getCreditsById(@Url url:String): Response<CreditsResponse>

    // PEOPLE

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getPeopleDetailsById(@Url url:String): Response<PeopleDetailsResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getPeopleMovieInterpretationsById(@Url url:String): Response<PeopleMovieInterpretationResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getPeopleSeriesInterpretationsById(@Url url:String): Response<PeopleSeriesInterpretationResponse>

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET
    suspend fun getPeopleMediaById(@Url url:String): Response<ImagePeopleResponse>

    // SEARCHES

    @Headers(Constants.ACCEPT_JSON,Constants.AUTHORIZATION_TOKEN_API)
    @GET()
    suspend fun getCollectionSearch(@Url url:String): Response<CollectionSearchResponse>


}