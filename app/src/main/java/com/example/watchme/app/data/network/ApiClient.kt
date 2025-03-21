package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.AccountResponse
import com.example.watchme.app.data.network.responses.CollectionResponse
import com.example.watchme.app.data.network.responses.CollectionSearchResponse
import com.example.watchme.app.data.network.responses.CreateListDto
import com.example.watchme.app.data.network.responses.CreateListResponse
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.CreditsResponse
import com.example.watchme.app.data.network.responses.Dtos.ItemListDto
import com.example.watchme.app.data.network.responses.EpisodeResponse
import com.example.watchme.app.data.network.responses.EpisodesRatedResponse
import com.example.watchme.app.data.network.responses.FavoriteRequestDto
import com.example.watchme.app.data.network.responses.FavoriteResponse
import com.example.watchme.app.data.network.responses.ImagePeopleResponse
import com.example.watchme.app.data.network.responses.ListDetailsResponse
import com.example.watchme.app.data.network.responses.ListsResponse
import com.example.watchme.app.data.network.responses.MediaProvidersResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.MovieSearchResponse
import com.example.watchme.app.data.network.responses.PeopleDetailsResponse
import com.example.watchme.app.data.network.responses.PeopleMovieInterpretationResponse
import com.example.watchme.app.data.network.responses.PeopleSeriesInterpretationResponse
import com.example.watchme.app.data.network.responses.PersonSearchResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.data.network.responses.RatedResponse
import com.example.watchme.app.data.network.responses.RatingRequestDto
import com.example.watchme.app.data.network.responses.RatingRequestResponse
import com.example.watchme.app.data.network.responses.ReviewsResponse
import com.example.watchme.app.data.network.responses.SeasonDetails
import com.example.watchme.app.data.network.responses.SeriesDetailsResponse
import com.example.watchme.app.data.network.responses.SeriesResponse
import com.example.watchme.app.data.network.responses.SeriesSearchResponse
import com.example.watchme.app.data.network.responses.VideoResponse
import com.example.watchme.app.data.network.responses.WatchListRequestDto
import com.example.watchme.app.data.network.responses.RequestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiClient {

    // MOVIES

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("region") country: String
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String,
        @Query("region") country: String
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("region") country: String
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("region") country: String
    ): Response<MovieResponse>

    @GET("watch/providers/movie")
    suspend fun getProviders(
        @Query("language") language: String,
        @Query("region") country: String
    ): Response<ProvidersResponse>

    @GET
    suspend fun getMovieDetailsById(
        @Url url: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<DetailsMovieResponse>


    @GET
    suspend fun getMoviesRecommendationsById(
        @Url url: String,
        @Query("language") language: String,
    ): Response<MovieResponse>

    @GET
    suspend fun getReviewsById(@Url url: String): Response<ReviewsResponse>

    // COLLECTIONS

    @GET
    suspend fun getCollectionDetailsById(
        @Url url: String,
        @Query("language") language: String,
    ): Response<CollectionResponse>

    // SERIES

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("language") language: String
    ): Response<SeriesResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodaySeries(
        @Query("language") language: String
    ): Response<SeriesResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirSeries(
        @Query("language") language: String
    ): Response<SeriesResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Query("language") language: String
    ): Response<SeriesResponse>

    @GET()
    suspend fun getSeriesDetailsById(
        @Url url: String,
        @Query("language") language: String,
    ): Response<SeriesDetailsResponse>

    @GET()
    suspend fun getSeasonsDetailsById(
        @Url url: String,
        @Query("language") language: String,
    ): Response<SeasonDetails>

    @GET()
    suspend fun getSeriesRecommendationsById(@Url url: String): Response<SeriesResponse>

    // EPISODES

    @GET()
    suspend fun getEpisodesDetailsById(
        @Url url: String,
        @Query("language") language: String,
    ): Response<EpisodeResponse>

    // SERIES & MOVIES

    @GET
    suspend fun getImageListById(@Url url: String): Response<ImageBackdrop>

    @GET
    suspend fun getVideosById(
        @Url url: String,
        @Query("language") language: String,
    ): Response<VideoResponse>

    @GET
    suspend fun getCreditsById(@Url url: String): Response<CreditsResponse>

    // PEOPLE

    @GET
    suspend fun getPeopleDetailsById(
        @Url url: String,
        @Query("language") language: String
    ): Response<PeopleDetailsResponse>

    @GET
    suspend fun getPeopleMovieInterpretationsById(
        @Url url: String,
        @Query("language") language: String

    ): Response<PeopleMovieInterpretationResponse>

    @GET
    suspend fun getPeopleSeriesInterpretationsById(
        @Url url: String,
        @Query("language") language: String
    ): Response<PeopleSeriesInterpretationResponse>

    @GET
    suspend fun getPeopleMediaById(@Url url: String): Response<ImagePeopleResponse>

    // SEARCHES

    @GET("search/collection")
    suspend fun getCollectionSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<CollectionSearchResponse>

    @GET("search/movie")
    suspend fun getMoviesSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<MovieSearchResponse>

    @GET("search/tv")
    suspend fun getSeriesSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<SeriesSearchResponse>

    @GET("search/person")
    suspend fun getPeopleSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<PersonSearchResponse>

    // RATING - MOVIES

    @POST
    suspend fun rateItem(
        @Body ratingRequest: RatingRequestDto,
        @Url url: String
    ): Response<RatingRequestResponse>

    @DELETE
    suspend fun deleteRate(@Url url: String): Response<RatingRequestResponse>

    // ACCOUNT

    @GET
    suspend fun getRatedMovies(@Url url: String): Response<RatedResponse>

    @GET
    suspend fun getRatedSeries(@Url url: String): Response<RatedResponse>

    @GET
    suspend fun getRatedSeriesEpisodes(@Url url: String): Response<EpisodesRatedResponse>

    @POST
    suspend fun addFavorite(
        @Body favoriteRequest: FavoriteRequestDto,
        @Url url: String
    ): Response<FavoriteResponse>

    @GET
    suspend fun getFavoritesMovies(@Url url: String): Response<MovieResponse>

    @GET
    suspend fun getFavoritesSeries(@Url url: String): Response<SeriesResponse>

    @POST
    suspend fun addToWatchList(
        @Body watchListRequest: WatchListRequestDto,
        @Url url: String
    ): Response<RequestResponse>

    @GET
    suspend fun getWatchlistMovies(@Url url: String): Response<MovieResponse>

    @GET
    suspend fun getWatchlistSeries(@Url url: String): Response<SeriesResponse>

    @GET
    suspend fun getAccountDetails(@Url url: String): Response<AccountResponse>

    @GET
    suspend fun getMyLists(@Url url: String): Response<ListsResponse>

    @POST("list")
    suspend fun createList(@Body createListDto: CreateListDto): Response<CreateListResponse>

    @POST
    suspend fun addMovieToList(
        @Url url: String,
        @Body itemListDto: ItemListDto
    ): Response<RequestResponse>

    @GET
    suspend fun checkMovieOnList(@Url url: String, @Query("movie_id") movieId: Int) : Response<RequestResponse>

    @DELETE
    suspend fun deleteList(@Url url: String): Response<RequestResponse>

    @POST
    suspend fun deleteItemFromList(
        @Url url: String,
        @Body deleteItemFromList: ItemListDto
    ): Response<RequestResponse>

    @POST
    suspend fun clearList(
        @Url url: String,
        @Query("confirm") confirm: Boolean = true
    ): Response<RequestResponse>

    @GET
    suspend fun getListDetails(@Url url: String): Response<ListDetailsResponse>

    // PROVIDERS

    @GET
    suspend fun getMovieProvidersByMovieId(@Url url: String): Response<MediaProvidersResponse>

    @GET
    suspend fun getSeriesProvidersBySeriesId(@Url url: String): Response<MediaProvidersResponse>


}

