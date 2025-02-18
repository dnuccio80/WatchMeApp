package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.ReviewDataClass
import javax.inject.Inject

class GetReviewsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(movieId: Int): List<ReviewDataClass> = apiRepository.getReviewsById(movieId)
}