package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    suspend fun getNowPlayingMovies(): MovieResponse {
        return apiService.getNowPlayingMovies()
    }

    suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    suspend fun getProviders(): ProvidersResponse{
        return apiService.getProviders()
    }

    suspend fun getMovieDetailsById(movieId:Int): DetailsMovieResponse {
        return apiService.getMovieDetailsById(movieId)
    }

}