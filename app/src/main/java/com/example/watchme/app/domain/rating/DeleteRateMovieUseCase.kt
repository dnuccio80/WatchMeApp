package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RatingRequestDataClass
import javax.inject.Inject

class DeleteRateMovieUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(movieId: Int) : RatingRequestDataClass = apiRepository.deleteRateMovie(movieId)
}