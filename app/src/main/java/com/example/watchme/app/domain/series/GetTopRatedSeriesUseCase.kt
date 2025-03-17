package com.example.watchme.app.domain.series

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import javax.inject.Inject

class GetTopRatedSeriesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(language: String): List<SeriesDataClass> = apiRepository.getTopRatedSeries(language)
}