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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class ApiService @Inject constructor(private val retrofit: Retrofit) {

    // MOVIES

    suspend fun getPopularMovies(): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getPopularMovies()
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getNowPlayingMovies(): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getNowPlayingMovies()
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getTopRatedMovies(): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getTopRatedMovies()
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getUpcomingMovies(): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getUpcomingMovies()
            val body: MovieResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getProviders(): ProvidersResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getProviders()
            val body: ProvidersResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                ProvidersResponse(emptyList())
            }
        }
    }

    suspend fun getMovieDetailsById(movieId: Int): DetailsMovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getMovieDetailsById("movie/$movieId${Constants.SPANISH_AR}")
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

    suspend fun getRecommendationsById(movieId: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getRecommendationsById("movie/${movieId}/recommendations")
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

    suspend fun getMovieVideosById(movieId: Int): VideoResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getVideosById("movie/${movieId}/videos")
            val body: VideoResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch videos: ${response.errorBody()?.string()}")
            }
        }
    }

    // COLLECTIONS

    suspend fun getCollectionDetailsById(collectionId: Int): CollectionResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getCollectionDetailsById("collection/$collectionId")
            val body: CollectionResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch collection: ${response.errorBody()?.string()}")
            }
        }
    }

    // SERIES

    suspend fun getPopularSeries(): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getPopularSeries()
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getAiringSeriesToday(): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getAiringTodaySeries()
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getOnTheAirSeries(): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getOnTheAirSeries()
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body

            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getTopRatedSeries(): SeriesResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java).getTopRatedSeries()
            val body: SeriesResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body

            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getSeriesDetailsById(seriesId: Int): SeriesDetailsResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getSeriesDetailsById("tv/${seriesId}")
            val body: SeriesDetailsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch series details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeasonDetailsById(seriesId: Int, seasonNumber: Int): SeasonDetails {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getSeasonsDetailsById("tv/$seriesId/season/$seasonNumber")
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

    suspend fun getSeriesVideosById(seriesId: Int): VideoResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getVideosById("tv/${seriesId}/videos")
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

    suspend fun getPeopleDetails(personId: Int): PeopleDetailsResponse {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(ApiClient::class.java).getPeopleDetailsById("person/$personId")
            val body: PeopleDetailsResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getPeopleMovieInterpretationsById(personId: Int): PeopleMovieInterpretationResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getPeopleMovieInterpretationsById("person/$personId/movie_credits")
            val body: PeopleMovieInterpretationResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getPeopleSeriesInterpretationsById(personId: Int): PeopleSeriesInterpretationResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getPeopleSeriesInterpretationsById("person/$personId/tv_credits")
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
        episodeNumber: Int
    ): EpisodeResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getEpisodesDetailsById("tv/$seriesId/season/$seasonNumber/episode/$episodeNumber")
            val body: EpisodeResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch episode details: ${response.errorBody()?.string()}")
            }
        }
    }

    // SEARCHES

    suspend fun getSearchCollection(query: String): CollectionSearchResponse {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ApiClient::class.java)
                .getCollectionSearch("search/collection?query=$query")
            val body: CollectionSearchResponse? = response.body()

            if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception("Failed to fetch searched collection: ${response.errorBody()?.string()}")
            }
        }
    }



}