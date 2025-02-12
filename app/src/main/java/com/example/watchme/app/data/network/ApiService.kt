package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.MovieCreditsResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class ApiService @Inject constructor(private val retrofit: Retrofit) {

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
            val response = retrofit.create(ApiClient::class.java).getMovieDetailsById("movie/$movieId")
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
            val response = retrofit.create(ApiClient::class.java).getMovieCreditsById("movie/${movieId}/credits")
            val body: MovieCreditsResponse? = response.body()

            if(response.isSuccessful && body != null){
                body
            } else {
                throw Exception("Failed to fetch movie details: ${response.errorBody()?.string()}")
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
                throw Exception("Failed to fetch movie details: ${response.errorBody()?.string()}")
            }
        }
    }
}