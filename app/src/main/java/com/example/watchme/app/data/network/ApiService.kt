package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.AccountResponse
import com.example.watchme.app.data.network.responses.CollectionResponse
import com.example.watchme.app.data.network.responses.CollectionSearchResponse
import com.example.watchme.app.data.network.responses.CreateListDto
import com.example.watchme.app.data.network.responses.CreateListResponse
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.CreditsResponse
import com.example.watchme.app.data.network.responses.Dtos.DeleteItemFromListDto
import com.example.watchme.app.data.network.responses.EpisodeResponse
import com.example.watchme.app.data.network.responses.EpisodesRatedResponse
import com.example.watchme.app.data.network.responses.FavoriteRequestDto
import com.example.watchme.app.data.network.responses.FavoriteResponse
import com.example.watchme.app.data.network.responses.ImagePeopleResponse
import com.example.watchme.app.data.network.responses.ListDetailsResponse
import com.example.watchme.app.data.network.responses.ListsResponse
import com.example.watchme.app.data.network.responses.MovieProvidersResponse
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
import com.example.watchme.core.constants.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class ApiService @Inject constructor(private val retrofit: Retrofit) {

    // MOVIES

    suspend fun getPopularMovies(language: String, country: String): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getPopularMovies(language, country)
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getNowPlayingMovies(language: String, region: String): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getNowPlayingMovies(language, region)
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getTopRatedMovies(language: String, region: String): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getTopRatedMovies(language, region)
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getUpcomingMovies(language: String, region: String): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getUpcomingMovies(language, region)
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getProviders(language: String, region: String): ProvidersResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getProviders(language, region)
            val body: ProvidersResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                ProvidersResponse(emptyList())
            }
        }
    }

    suspend fun getMovieDetailsById(
        movieId: Int,
        language: String,
        region: String
    ): DetailsMovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getMovieDetailsById("movie/$movieId", language, region)
            val body: DetailsMovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getMovieCreditsById(movieId: Int): CreditsResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getCreditsById("movie/${movieId}/credits${Constants.SPANISH_AR}")
            val body: CreditsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getMovieImageListById(movieId: Int): ImageBackdrop {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getImageListById("movie/$movieId/images")
            val body: ImageBackdrop? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch image backdrop: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getRecommendationsById(movieId: Int, language: String): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getMoviesRecommendationsById("movie/${movieId}/recommendations", language)
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to fetch movie recommendations: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getReviewsById(movieId: Int): ReviewsResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getReviewsById("movie/${movieId}/reviews")
            val body: ReviewsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch reviews: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getMovieVideosById(movieId: Int, language: String): VideoResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getVideosById("movie/${movieId}/videos", language)
            val body: VideoResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch videos: ${response.errorBody()?.string()}")
            }
        }
    }

    // COLLECTIONS

    suspend fun getCollectionDetailsById(collectionId: Int, language: String): CollectionResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java)
                    .getCollectionDetailsById("collection/$collectionId", language)
            val body: CollectionResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch collection: ${response.errorBody()?.string()}")
            }
        }
    }

    // SERIES

    suspend fun getPopularSeries(language: String): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getPopularSeries(language)
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getAiringSeriesToday(language: String): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getAiringTodaySeries(language)
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getOnTheAirSeries(language: String): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getOnTheAirSeries(language)
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body

            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getTopRatedSeries(language: String): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getTopRatedSeries(language)
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body

            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getSeriesDetailsById(seriesId: Int, language: String): SeriesDetailsResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java)
                    .getSeriesDetailsById("tv/${seriesId}", language)
            val body: SeriesDetailsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch series details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeasonDetailsById(
        seriesId: Int,
        seasonNumber: Int,
        language: String
    ): SeasonDetails {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getSeasonsDetailsById("tv/$seriesId/season/$seasonNumber", language)
            val body: SeasonDetails? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch series details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeriesRecommendationsById(seriesId: Int): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getSeriesRecommendationsById("tv/$seriesId/recommendations")
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch series details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeriesImageListById(seriesId: Int): ImageBackdrop {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getImageListById("tv/${seriesId}/images")
            val body: ImageBackdrop? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch image backdrop: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeriesVideosById(seriesId: Int, language: String): VideoResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getVideosById("tv/${seriesId}/videos", language)
            val body: VideoResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch videos: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeriesCreditsById(seriesId: Int): CreditsResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getCreditsById("tv/${seriesId}/credits${Constants.SPANISH_AR}")
            val body: CreditsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    // PEOPLE

    suspend fun getPeopleDetails(personId: Int, language: String): PeopleDetailsResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getPeopleDetailsById("person/$personId", language)
            val body: PeopleDetailsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getPeopleMovieInterpretationsById(personId: Int, language: String): PeopleMovieInterpretationResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getPeopleMovieInterpretationsById("person/$personId/movie_credits", language)
            val body: PeopleMovieInterpretationResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getPeopleSeriesInterpretationsById(personId: Int, language: String): PeopleSeriesInterpretationResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getPeopleSeriesInterpretationsById("person/$personId/tv_credits", language)
            val body: PeopleSeriesInterpretationResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getPeopleMediaById(personId: Int): ImagePeopleResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getPeopleMediaById("person/$personId/images")
            val body: ImagePeopleResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getEpisodesDetailsById(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String
    ): EpisodeResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getEpisodesDetailsById(
                    "tv/$seriesId/season/$seasonNumber/episode/$episodeNumber",
                    language
                )
            val body: EpisodeResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to fetch episode details: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    // SEARCHES

    suspend fun getSearchCollection(query: String, language: String, region: String): CollectionSearchResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getCollectionSearch(query, language, region)
            val body: CollectionSearchResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to fetch searched collection: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getSearchMovies(query: String, language: String, region: String): MovieSearchResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getMoviesSearch(query, language, region)
            val body: MovieSearchResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to fetch searched collection: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getSearchSeries(query: String, language: String, region: String): SeriesSearchResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getSeriesSearch(query, language, region)
            val body: SeriesSearchResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to fetch searched collection: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getSearchPeople(query: String, language: String, region: String): PersonSearchResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getPeopleSearch(query, language, region)
            val body: PersonSearchResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to fetch searched collection: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    // RATING MOVIE

    suspend fun rateMovie(movieId: Int, rating: Float): RatingRequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .rateItem(RatingRequestDto(rating), "movie/$movieId/rating")
            val body: RatingRequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to rate movie: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun deleteRateMovie(movieId: Int): RatingRequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .deleteRate("movie/$movieId/rating")
            val body: RatingRequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to delete rate movie: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }


    suspend fun rateSeries(seriesId: Int, rating: Float): RatingRequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .rateItem(RatingRequestDto(rating), "tv/$seriesId/rating")
            val body: RatingRequestResponse? = response.body()
            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to rate series: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun deleteRateSeries(seriesId: Int): RatingRequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .deleteRate("tv/$seriesId/rating")
            val body: RatingRequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to delete rate: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun rateSeriesEpisodes(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        rating: Float
    ): RatingRequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .rateItem(
                    RatingRequestDto(rating),
                    "tv/$seriesId/season/$seasonNumber/episode/$episodeNumber/rating"
                )
            val body: RatingRequestResponse? = response.body()
            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to rate series episodes: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun deleteRateSeriesEpisodes(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): RatingRequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .deleteRate("tv/$seriesId/season/$seasonNumber/episode/$episodeNumber/rating")
            val body: RatingRequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to delete rate series episode: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    // ACCOUNT

    suspend fun getRatedMovies(accountId: Int = 0): RatedResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getRatedMovies("account/$accountId/rated/movies")
            val body: RatedResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get rated movies: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }


    suspend fun getRatedSeries(accountId: Int = 0): RatedResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getRatedSeries("account/$accountId/rated/tv")
            val body: RatedResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get rated series: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getRatedSeriesEpisodes(accountId: Int = 0): EpisodesRatedResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getRatedSeriesEpisodes("account/$accountId/rated/tv/episodes")
            val body: EpisodesRatedResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get rated series episodes: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun addFavorite(
        mediaId: Int,
        mediaType: String,
        favorite: Boolean,
        accountId: Int
    ): FavoriteResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .addFavorite(
                    FavoriteRequestDto(
                        mediaId = mediaId,
                        mediaType = mediaType,
                        favorite = favorite
                    ), url = "account/$accountId/favorite"
                )
            val body: FavoriteResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get favorites: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getFavoritesMovies(accountId: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getFavoritesMovies("account/$accountId/favorite/movies")
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get favorites movies: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getFavoritesSeries(accountId: Int): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getFavoritesSeries("account/$accountId/favorite/tv")
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get favorites series: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun addToWatchList(
        mediaId: Int,
        mediaType: String,
        watchlist: Boolean,
        accountId: Int
    ): RequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .addToWatchList(
                    WatchListRequestDto(
                        mediaId = mediaId,
                        mediaType = mediaType,
                        watchlist = watchlist
                    ), url = "account/$accountId/watchlist"
                )
            val body: RequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get watchlist: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getWatchListMovies(accountId: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getWatchlistMovies("account/$accountId/watchlist/movies")
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get watchlist movies: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getWatchListSeries(accountId: Int): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getWatchlistSeries("account/$accountId/watchlist/tv")
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get watchlist series: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getAccountDetails(accountId: Int): AccountResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getAccountDetails("account/$accountId")
            val body: AccountResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get account details: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getMyLists(accountId: Int): ListsResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getMyLists("account/$accountId/lists")
            val body: ListsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get my lists: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun createList(
        accountId: Int,
        name: String,
        description: String,
        language: String
    ): CreateListResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .createList(
                    CreateListDto(
                        name = name,
                        description = description,
                        language = language
                    )
                )
            val body: CreateListResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to create the list: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun deleteList(listId: Int): RequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .deleteList("list/$listId")
            val body: RequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to delete list: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun getListDetails(listId: Int): ListDetailsResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getListDetails("list/$listId")
            val body: ListDetailsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get list details: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun deleteItemFromList(listId: Int, itemId: Int): RequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .deleteItemFromList(
                    url = "list/$listId/remove_item",
                    DeleteItemFromListDto(itemId)
                )
            val body: RequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to delete list item: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    suspend fun clearList(listId: Int): RequestResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .clearList(url = "list/$listId/clear")
            val body: RequestResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to clear list: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }

    // PROVIDERS

    suspend fun getMovieProvidersByMovieId(movieId:Int): MovieProvidersResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getMovieProvidersByMovieId(url = "movie/$movieId/watch/providers")
            val body: MovieProvidersResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception(
                    "Failed to get movie providers: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }
    }


}