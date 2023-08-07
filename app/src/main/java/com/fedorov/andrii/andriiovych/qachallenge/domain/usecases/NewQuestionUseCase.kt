package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewQuestionUseCase @Inject constructor(private val networkRepository: NetworkRepository) {

    suspend fun getNewQuestion(questionParams: QuestionParams): Flow<Resource<QuestionModel>> =
        networkRepository.getNewQuestion(questionParams)
}
