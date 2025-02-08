package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.Movie
import com.example.watchme.app.data.network.responses.MovieResponse
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

}