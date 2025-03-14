package com.example.watchme.app.domain.favorites

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.FavoriteDataClass
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(mediaId: Int, mediaType: String, favorite: Boolean, accountId: Int): FavoriteDataClass {
        return apiRepository.addFavorites(mediaId, mediaType, favorite, accountId)
    }
}