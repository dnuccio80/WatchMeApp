package com.example.watchme.app.domain

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.MovieResponse
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val apiRepository: ApiRepository){
    suspend operator fun invoke(): MovieResponse {
        return apiRepository.getPopularMovies()
    }
}