package com.example.watchme.app.domain.providers

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.MediaProviderDataClass
import javax.inject.Inject

class GetSeriesProvidersBySeriesIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId:Int) : MediaProviderDataClass = apiRepository.getSeriesProvidersBySeriesId(seriesId)
}