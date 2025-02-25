package com.example.watchme.app.domain.movies

import com.example.watchme.app.data.network.ApiRepository
import com.example.watchme.app.ui.dataClasses.BackdropImageDataClass
import javax.inject.Inject

class GetImageListByIdUseCase @Inject constructor(private val apiRepository: ApiRepository)  {
    suspend operator fun invoke(movieId: Int) : List<BackdropImageDataClass> = apiRepository.getMovieImageListById(movieId)
}