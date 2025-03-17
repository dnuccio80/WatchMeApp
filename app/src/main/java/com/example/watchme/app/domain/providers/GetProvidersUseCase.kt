package com.example.watchme.app.domain.providers

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.data.network.responses.ProvidersResponse
import com.example.watchme.app.ui.dataClasses.ProvidersDataClass
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(language: String, region: String): List<ProvidersDataClass> {
        return apiRepository.getProviders(language, region)
    }
}