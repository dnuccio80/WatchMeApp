package com.example.watchme.app.domain.series

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetSeriesCreditsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId: Int) = apiRepository.getSeriesCreditsById(seriesId)
}