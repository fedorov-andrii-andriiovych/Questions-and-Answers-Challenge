package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import javax.inject.Inject

class NewTokenUseCase @Inject constructor(private val networkRepository: NetworkRepository) {

    suspend fun getNewToken():Boolean{
       return networkRepository.getNewToken()
    }

}