package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetWatchlistSeriesUseCase @Inject constructor(private val apiRepository: ApiRepository){
    suspend operator fun invoke(accountId: Int) = apiRepository.getWatchlistSeries(accountId)
}