package com.example.watchme.app.domain.series

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.SeriesDetailsResponse
import javax.inject.Inject

class GetSeriesDetailsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId:Int): SeriesDetailsResponse = apiRepository.getSeriesDetailsById(seriesId)
}