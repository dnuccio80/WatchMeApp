package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.SeriesDataClass
import javax.inject.Inject

class GetRatedSeriesUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int = 0): List<SeriesDataClass> = apiRepository.getRatedSeries(accountId)
}