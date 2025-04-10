package com.example.watchme.app.domain.people

import com.example.watchme.app.data.network.ApiRepository
import javax.inject.Inject

class GetPeopleDetailsByIdUseCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(personId: Int, language: String) = apiRepository.getPeopleDetailsById(personId, language)
}