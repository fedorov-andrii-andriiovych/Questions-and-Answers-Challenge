package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.domain.models.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.repositories.NetworkRepository
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.ResultOf
import javax.inject.Inject

class NewQuestionUseCase @Inject constructor(private val networkRepository: NetworkRepository) {

    suspend fun getNewQuestion(category: Int, difficulty: String, type: String): ResultOf<QuestionModel> =
        networkRepository.getNewQuestion(
            category = category,
            type = type,
            difficulty = difficulty
        )
}
