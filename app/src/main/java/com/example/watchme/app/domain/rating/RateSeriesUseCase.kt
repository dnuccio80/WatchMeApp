package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RatingDataClass
import javax.inject.Inject

class RateSeriesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(rating: Float, seriesId: Int) : RatingDataClass = apiRepository.rateSeries(rating, seriesId)
}