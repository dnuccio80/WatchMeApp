package com.example.watchme.app.domain.collections

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetCollectionDetailsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(collectionId: Int, language: String) = apiRepository.getCollectionDetailsById(collectionId, language)
}