package com.example.watchme.app.data.network

import com.example.watchme.app.data.network.responses.DetailsMovieResponse
import com.example.watchme.app.data.network.responses.ImageBackdrop
import com.example.watchme.app.data.network.responses.MovieCreditsResponse
import com.example.watchme.app.data.network.responses.MovieResponse
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.data.network.responses.toDetailsMovieDataClass
import com.example.watchme.app.data.network.responses.toMovieDataClass
import com.example.watchme.app.data.network.responses.toProvidersDataClass
import com.example.watchme.app.ui.dataClasses.DetailsMovieDataClass
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPopularMovies(): List<MovieDataClass> {
        return apiService.getPopularMovies().toMovieDataClass()
    }

    suspend fun getNowPlayingMovies(): List<MovieDataClass> {
        return apiService.getNowPlayingMovies().toMovieDataClass()
    }

    suspend fun getTopRatedMovies(): List<MovieDataClass> {
        return apiService.getTopRatedMovies().toMovieDataClass()
    }

    suspend fun getProviders(): List<ProvidersDataClass> {
        return apiService.getProviders().toProvidersDataClass()
    }

    suspend fun getMovieDetailsById(movieId: Int): DetailsMovieDataClass {
        return apiService.getMovieDetailsById(movieId).toDetailsMovieDataClass()
    }

    suspend fun getMovieCreditsById(movieId: Int): MovieCreditsResponse {
        return apiService.getMovieCreditsById(movieId)
    }

    suspend fun getImageListById(movieId: Int): ImageBackdrop {
        return apiService.getImageListById(movieId)
    }
}