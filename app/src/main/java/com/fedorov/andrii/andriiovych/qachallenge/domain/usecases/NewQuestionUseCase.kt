package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionParams
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.ResultOfResponse
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOfScreen
import javax.inject.Inject

class NewQuestionUseCase @Inject constructor(private val networkRepository: NetworkRepository) {

    suspend fun getNewQuestion(questionParams: QuestionParams): ResultOfResponse<QuestionModel> =
        networkRepository.getNewQuestion(questionParams)
}
