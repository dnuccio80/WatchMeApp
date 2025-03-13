package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RatingRequestDataClass
import javax.inject.Inject

class RateMovieUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(rating: Float, movieId: Int) : RatingRequestDataClass = apiRepository.rateMovie(rating, movieId)
}