package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.MovieDataClass
import javax.inject.Inject

class GetRecommendationsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(movieId: Int, language: String): List<MovieDataClass> = apiRepository.getRecommendationsById(movieId, language)
}