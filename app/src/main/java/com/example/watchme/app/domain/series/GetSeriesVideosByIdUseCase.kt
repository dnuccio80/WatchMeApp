package com.example.watchme.app.domain.series

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetSeriesVideosByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId: Int, language: String) = apiRepository.getSeriesVideosById(seriesId, language)
}