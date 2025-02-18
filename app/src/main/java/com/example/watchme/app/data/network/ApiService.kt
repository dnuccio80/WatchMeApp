package com.example.watchme.app.data.network

import android.util.Log
import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.Movie
import com.example.watchme.app.data.network.responses.MovieCreditsResponse
import com.example.watchme.app.data.network.responses.MovieResponse
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
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getPopularMovies()
            val body: MovieResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getNowPlayingMovies(): MovieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getNowPlayingMovies()
            val body: MovieResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getTopRatedMovies(): MovieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getTopRatedMovies()
            val body: MovieResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getUpcomingMovies(): MovieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getUpcomingMovies()
            val body: MovieResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                MovieResponse(emptyList())
            }
        }
    }

    suspend fun getProviders(): ProvidersResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getProviders()
            val body: ProvidersResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else{
                ProvidersResponse(emptyList())
            }
        }
    }

    suspend fun getMovieDetailsById(movieId:Int): DetailsMovieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getMovieDetailsById("movie/$movieId${Constants.SPANISH_AR}")
            val body: DetailsMovieResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch movie details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getMovieCreditsById(movieId:Int): MovieCreditsResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getMovieCreditsById("movie/${movieId}/credits${Constants.SPANISH_AR}")
            val body: MovieCreditsResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch movie credits: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getImageListById(movieId:Int): ImageBackdrop {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getImageListById("movie/$movieId/images")
            val body: ImageBackdrop? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch image backdrop: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getRecommendationsById(movieId:Int): MovieResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getRecommendationsById("movie/${movieId}/recommendations")
            val body: MovieResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch movie recommendations: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getReviewsById(movieId:Int): ReviewsResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getReviewsById("movie/${movieId}/reviews")
            val body: ReviewsResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch reviews: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getVideosById(movieId:Int): VideoResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getVideosById("movie/${movieId}/videos")
            val body: VideoResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch videos: ${response.errorBody()?.string()}")
            }
        }
    }

    // SERIES

    suspend fun getPopularSeries(): SeriesResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getPopularSeries()
            val body: SeriesResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getAiringSeriesToday(): SeriesResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getAiringTodaySeries()
            val body: SeriesResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getOnTheAirSeries(): SeriesResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getOnTheAirSeries()
            val body: SeriesResponse? = response.body()

            if(response.isSuccessful && body != null){
                body

            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getTopRatedSeries(): SeriesResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getTopRatedSeries()
            val body: SeriesResponse? = response.body()

            if(response.isSuccessful && body != null){
                body

            } else {
                SeriesResponse(emptyList())
            }
        }
    }

    suspend fun getSeriesDetailsById(seriesId:Int): SeriesDetailsResponse {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getSeriesDetailsById("tv/${seriesId}")
            val body: SeriesDetailsResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch series details: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun getSeasonDetailsById(seriesId:Int, seasonNumber:Int): SeasonDetails {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiClient::class.java).getSeasonsDetailsById("tv/$seriesId/season/$seasonNumber")
            val body: SeasonDetails? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch series details: ${response.errorBody()?.string()}")
            }
        }
    }

}