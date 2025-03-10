package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(
        mediaId: Int,
        mediaType: String,
        watchlist: Boolean,
        accountId: Int
    ) = apiRepository.addToWatchlist(
        mediaId = mediaId,
        mediaType = mediaType,
        watchlist = watchlist,
        accountId = accountId
    )
}    