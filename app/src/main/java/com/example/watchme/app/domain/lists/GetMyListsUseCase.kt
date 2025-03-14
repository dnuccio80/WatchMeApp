package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.ListDataClass
import javax.inject.Inject

class GetMyListsUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(accountId:Int) : List<ListDataClass> = apiRepository.getMyLists(accountId)
}