package com.example.watchme.app.domain.account

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId: Int) = apiRepository.getAccountDetails(accountId)
}