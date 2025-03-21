package com.example.watchme.app.domain.lists

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.ItemOnListDataClass
import com.example.watchme.app.ui.dataClasses.RequestResponseDataClass
import javax.inject.Inject

class CheckIfMovieIsOnListUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(listId:Int, movieId:Int): ItemOnListDataClass = apiRepository.checkMovieOnList(listId, movieId)
}