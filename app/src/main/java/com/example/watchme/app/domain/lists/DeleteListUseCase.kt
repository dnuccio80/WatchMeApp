package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.RequestResponseDataClass
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(listId:Int) : RequestResponseDataClass = apiRepository.deleteList(listId)
}