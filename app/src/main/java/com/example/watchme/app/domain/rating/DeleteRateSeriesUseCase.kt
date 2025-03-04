package com.example.watchme.app.domain.rating

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RatingDataClass
import javax.inject.Inject

class DeleteRateSeriesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId: Int) : RatingDataClass = apiRepository.deleteRateSeries(seriesId)
}