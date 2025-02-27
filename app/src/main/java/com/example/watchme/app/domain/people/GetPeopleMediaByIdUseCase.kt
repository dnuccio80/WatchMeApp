package com.example.watchme.app.domain.people

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import javax.inject.Inject

class GetPeopleMediaByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(personId: Int) : List<BackdropImageDataClass> = apiRepository.getPeopleMediaById(personId)
}