package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId:Int, name:String, description:String, language:String) = apiRepository.createList(accountId, name, description, language)
}