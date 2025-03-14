package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RequestResponseDataClass
import javax.inject.Inject

class DeleteItemFromListUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(listId: Int, itemId: Int) : RequestResponseDataClass = apiRepository.deleteItemFromList(listId = listId, itemId = itemId)
}