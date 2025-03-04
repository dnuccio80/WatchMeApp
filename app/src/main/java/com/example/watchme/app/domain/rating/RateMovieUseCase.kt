package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class RateMovieUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(rating: Float, movieId: Int) = apiRepository.rateMovie(rating, movieId)
}