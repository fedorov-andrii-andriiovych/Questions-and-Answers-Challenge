package com.fedorov.andrii.andriiovych.qachallenge.domain.usecases

import com.fedorov.andrii.andriiovych.qachallenge.data.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.data.repositories.NetworkRepository
import javax.inject.Inject

class NewQuestionUseCase @Inject constructor(private val networkRepository: NetworkRepository) {

    suspend fun getNewQuestion(category: Int, difficulty: String, type: String): QuestionModel =
        networkRepository.getNewQuestion(
            category = category,
            type = type,
            difficulty = difficulty
        )
}
