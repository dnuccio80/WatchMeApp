package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetListDetailsUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(listId: Int) = apiRepository.getListDetails(listId)
}