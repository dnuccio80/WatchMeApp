package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.CollectionResponse
import com.example.watchme.app.data.network.responses.CollectionSearchResponse
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.CreditsResponse
import com.example.watchme.app.data.network.responses.EpisodeResponse
import com.example.watchme.app.data.network.responses.ImagePeopleResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.MovieSearchResponse
import com.example.watchme.app.data.network.responses.PeopleDetailsResponse
import com.example.watchme.app.data.network.responses.PeopleMovieInterpretationResponse
import com.example.watchme.app.data.network.responses.PeopleSeriesInterpretationResponse
import com.example.watchme.app.data.network.responses.PersonSearch
import com.example.watchme.app.data.network.responses.PersonSearchResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.data.network.responses.ReviewsResponse
import com.example.watchme.app.data.network.responses.SeasonDetails
import com.example.watchme.app.data.network.responses.SeriesDetailsResponse
import com.example.watchme.app.data.network.responses.SeriesResponse
import com.example.watchme.app.data.network.responses.SeriesSearchResponse
import com.example.watchme.app.data.network.responses.VideoResponse
import com.example.watchme.core.constants.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiClient {

    // MOVIES

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieResponse>

    @GET("watch/providers/movie${Constants.SPANISH_AR}${Constants.AR_REGION}")
    suspend fun getProviders(): Response<ProvidersResponse>

    @GET
    suspend fun getMovieDetailsById(@Url url:String): Response<DetailsMovieResponse>


    @GET
    suspend fun getRecommendationsById(@Url url:String): Response<MovieResponse>

    @GET
    suspend fun getReviewsById(@Url url:String): Response<ReviewsResponse>

    // COLLECTIONS

    @GET
    suspend fun getCollectionDetailsById(@Url url:String): Response<CollectionResponse>

    // SERIES

    @GET("tv/popular")
    suspend fun getPopularSeries(): Response<SeriesResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodaySeries(): Response<SeriesResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirSeries(): Response<SeriesResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(): Response<SeriesResponse>

    @GET()
    suspend fun getSeriesDetailsById(@Url url:String): Response<SeriesDetailsResponse>

    @GET()
    suspend fun getSeasonsDetailsById(@Url url:String): Response<SeasonDetails>

    @GET()
    suspend fun getSeriesRecommendationsById(@Url url:String): Response<SeriesResponse>

    // EPISODES

    @GET()
    suspend fun getEpisodesDetailsById(@Url url:String): Response<EpisodeResponse>

    // SERIES & MOVIES

    @GET
    suspend fun getImageListById(@Url url:String): Response<ImageBackdrop>

    @GET
    suspend fun getVideosById(@Url url:String): Response<VideoResponse>

    @GET
    suspend fun getCreditsById(@Url url:String): Response<CreditsResponse>

    // PEOPLE

    @GET
    suspend fun getPeopleDetailsById(@Url url:String): Response<PeopleDetailsResponse>

    @GET
    suspend fun getPeopleMovieInterpretationsById(@Url url:String): Response<PeopleMovieInterpretationResponse>

    @GET
    suspend fun getPeopleSeriesInterpretationsById(@Url url:String): Response<PeopleSeriesInterpretationResponse>

    @GET
    suspend fun getPeopleMediaById(@Url url:String): Response<ImagePeopleResponse>

    // SEARCHES

    @GET()
    suspend fun getCollectionSearch(@Url url:String): Response<CollectionSearchResponse>

    @GET()
    suspend fun getMoviesSearch(@Url url:String): Response<MovieSearchResponse>

    @GET()
    suspend fun getSeriesSearch(@Url url:String): Response<SeriesSearchResponse>

    @GET()
    suspend fun getPeopleSearch(@Url url:String): Response<PersonSearchResponse>

    // RATING

//    @POST
//    suspend fun postRating(): Response<Unit>

}