package com.fedorov.andrii.andriiovych.qachallenge.repositories

import com.fedorov.andrii.andriiovych.qachallenge.model.QuestionModel
import com.fedorov.andrii.andriiovych.qachallenge.network.QuestionServices
import com.fedorov.andrii.andriiovych.qachallenge.network.RetrofitQuestionsClient

class RetrofitNetworkRepositoryImpl(private val questionServices: QuestionServices = RetrofitQuestionsClient.create()) :
    NetworkRepository {

    override suspend fun getNewQuestion(): QuestionModel {
        val questionResponse = questionServices.getNewQuestion().results[0]
        return QuestionModel(
            category = questionResponse.category,
            type = questionResponse.type,
            difficulty = questionResponse.difficulty,
            question = questionResponse.question,
            correct_answer = questionResponse.correctAnswer,
            incorrect_answers = questionResponse.incorrectAnswers
        )
    }
}