package com.example.watchme.app.domain.series

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import javax.inject.Inject

class GetSeriesImageListByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(seriesId:Int) : List<BackdropImageDataClass> = apiRepository.getSeriesImageListById(seriesId)
}